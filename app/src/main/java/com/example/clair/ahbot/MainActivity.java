package com.example.clair.ahbot;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

import ai.kitt.snowboy.SnowboyDetect;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Fulfillment;
import ai.api.model.Result;
import ai.kitt.snowboy.SnowboyDetect;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    //region firebase database
//    private FirebaseDatabase fFirebaseDatabase;
//    private DatabaseReference fDatabaseReference;
//    private ChildEventListener fChildEventListener;
//    Firestore f;
    //endregion

    //region firebase auth
    private FirebaseAuth fFirebaseAuth;
    private FirebaseAuth.AuthStateListener fAuthStateListener;
    private GoogleSignInClient mGoogleSignInClient;
    //endregion
    public static final int PERMISSION_REQUEST = 200;

    private boolean isShown = false;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);

        }

        //region auth
        fFirebaseAuth= FirebaseAuth.getInstance();
        fAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user==null){
                    SignInUpDialog signInUpDialog=new SignInUpDialog(MainActivity.this);
                    signInUpDialog.setCancelable(true);
                    signInUpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    signInUpDialog.show();
                    isShown = true;
                }
            }
        };

        //endregion

        //region setup tts
        if (!isShown) { //CHANGE THIS TO FALSE AFTER SIGN IN
            // TODO: Setup Components
            setupViews();
            setupXiaoBaiButton();
            setupAsr();
            setupTts();
            setupNlu();
            setupHotword();
            // TODO: Start Hotword
            startHotword();
        }
        //endregion
    }


//region menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                return true;
            case R.id.menu_schedule:
                Intent intent=new Intent(MainActivity.this,Schedule.class);
                startActivity(intent);
                return true;
            case R.id.menu_settings:
                //TODO: create settings page
                return true;
            case R.id.menu_signOut:
                FirebaseAuth.getInstance().signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //endregion


    // View Variables
    private Button button;
    private TextView textView;

    // ASR Variables
    private SpeechRecognizer speechRecognizer; //initialise this in setupASR method

    // TTS Variables
    private TextToSpeech textToSpeech;

    // NLU Variables
    private AIDataService aiDataService;

    // Hotword Variables
    private boolean shouldDetect;
    private SnowboyDetect snowboyDetect;

    static {
        System.loadLibrary("snowboy-detect-android");
    }


    private void setupViews() {
        // TODO: Setup Views
        textView = findViewById(R.id.tvSpeech);
        button = findViewById(R.id.buttonSpeech);

        // Press ctrl + space to suggest methods
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    if (textView.getText().toString() == "Good Morning") {
                //        textView.setText("Splasholy Wata");
                //    } else {
                //         textView.setText("Good Morning");
                //     }

                //startAsr();

                //TO stop hotword detection when button is pressed
                shouldDetect = false; // prevent startAsr from running two times
            }
        });

        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            Log.e("logged in", "NOT logged in");
        } else{
            Log.e("logged in", "IS logged in Email: :" + FirebaseAuth.getInstance().getCurrentUser().getEmail() );
        }

    }

    private void setupXiaoBaiButton() {
        String BUTTON_ACTION = "com.gowild.action.clickDown_action";
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BUTTON_ACTION);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO: Add action to do after button press is detected
                //startAsr();
                shouldDetect = false; // prevent startAsr from running two times
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private void setupAsr() {
        // TODO: Setup ASR --> Change Voice into Text
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) { // called when SR is ready to listen to you --> in here put showing faces or playing sound

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) { //when speech volume changes

            }

            @Override
            public void onBufferReceived(byte[] buffer) { //when receiving buffer of audio data

            }

            @Override
            public void onEndOfSpeech() { //when you stop speaking call this

            }

            @Override
            public void onError(int error) { //
                Log.e("asr", "Error: " + Integer.toString(error));
                startHotword();
            }

            @Override
            public void onResults(Bundle results) { //get asr results as list of strings
                List<String> texts = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (texts == null || texts.isEmpty()) {
                    textView.setText("Please try again");
                } else {
                    String text = texts.get(0);

                    //  String response;
                    //   if (text.equalsIgnoreCase("hello")) {
                    //       response = "hi there";
                    //  } else {
                    //     response = "i don't know what you're saying";
                    //   }
                    textView.setText(text);
                    //     startTts(response);
                    startNlu(text);
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
    }

    private void startAsr() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // TODO: Set Language
                final Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en");
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);

                // Stop hotword detection in case it is still running
                shouldDetect = false;

                // TODO: Start ASR
                speechRecognizer.startListening(recognizerIntent);
            }
        };
        Threadings.runInMainThread(this, runnable);
    }

    private void setupTts() {
        // TODO: Setup TTS
        textToSpeech = new TextToSpeech(this, null);

    }

    private void startTts(String text) {
        // TODO: Start TTS
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);

        // TODO: Wait for end and start hotword
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (textToSpeech.isSpeaking()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Log.e("tts", e.getMessage(), e);
                    }
                }

                startHotword();
            }
        };
        Threadings.runInBackgroundThread(runnable);
    }

    private void setupNlu() {
        // TODO: Change Client Access Token
        String clientAccessToken = "7900db35e2764da4b67fcae8e0adb09c";
        AIConfiguration aiConfiguration = new AIConfiguration(clientAccessToken,
                AIConfiguration.SupportedLanguages.English);
        aiDataService = new AIDataService(aiConfiguration);
    }

    private void startNlu(final String text) {
        // TODO: Start NLU
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                AIRequest aiRequest = new AIRequest();
                aiRequest.setQuery(text);


                try {
                    AIResponse aiResponse = aiDataService.request(aiRequest); // need exception because internet might not work --> Alt + Enter

                    Result result = aiResponse.getResult();
                    Fulfillment fulfillment = result.getFulfillment();
                    String speech = fulfillment.getSpeech();

                    String responseText;
                    if (speech.equalsIgnoreCase("weather_function")) {
                        responseText = getWeather();}
                       else if (speech.equalsIgnoreCase("medicine_function")) {
                            responseText = getMedicine();
                    } else if (speech.equalsIgnoreCase("okay")) {
                        responseText = speech;
                        Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                        startActivity(intent);

                    } else if (speech.equalsIgnoreCase("redirecting you to schedule page")){
                        responseText=speech;
                        Intent intent=new Intent(MainActivity.this,Schedule.class);
                        startActivity(intent);
                    }
                    else {
                        responseText = speech;
                    }

                    startTts(responseText);
                } catch (AIServiceException e) {
                    e.printStackTrace();
                }
            }
        };
        Threadings.runInBackgroundThread(runnable);
    }

    private void setupHotword() {
        shouldDetect = false;
        com.example.clair.ahbot.SnowboyUtils.copyAssets(this);

        // TODO: Setup Model File
        File snowboyDirectory = com.example.clair.ahbot.SnowboyUtils.getSnowboyDirectory();
        File model = new File(snowboyDirectory, "alexa_02092017.pmdl");
        File common = new File(snowboyDirectory, "common.res");

        // TODO: Set Sensitivity
        snowboyDetect = new SnowboyDetect(common.getAbsolutePath(), model.getAbsolutePath());
        snowboyDetect.setSensitivity("0.60"); //Change to lower sensitivity --> False Positives
        snowboyDetect.applyFrontend(true);
    }



    private void startHotword() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                shouldDetect = true;
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);
                int bufferSize = 3200;
                byte[] audioBuffer = new byte[bufferSize];
                AudioRecord audioRecord = new AudioRecord(
                        MediaRecorder.AudioSource.DEFAULT,
                        16000,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT,
                        bufferSize
                );

                if (audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
                    Log.e("hotword", "audio record fail to initialize");
                    return;
                }

                audioRecord.startRecording();
                Log.d("hotword", "start listening to hotword");

                while (shouldDetect) {
                    audioRecord.read(audioBuffer, 0, audioBuffer.length);

                    short[] shortArray = new short[audioBuffer.length / 2];
                    ByteBuffer.wrap(audioBuffer).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shortArray);

                    int result = snowboyDetect.runDetection(shortArray, shortArray.length);
                    if (result > 0) {
                        Log.d("hotword", "detected");
                        shouldDetect = false;
                    }
                }

                audioRecord.stop();
                audioRecord.release();
                Log.d("hotword", "stop listening to hotword");

                // TODO: Add action after hotword is detected
                startAsr();
            }
        };
        Threadings.runInBackgroundThread(runnable);
    }


    private String getWeather() {
        // TODO: (Optional) Get Weather Data via REST API

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.data.gov.sg/v1/environment/2-hour-weather-forecast")
                .addHeader("accept", "application/json")
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            String responseBody = response.body().string();

            JSONObject jsonObject = new JSONObject(responseBody);
            JSONArray forecasts = jsonObject.getJSONArray("items")
                    .getJSONObject(0)
                    .getJSONArray("forecasts");

            for (int i = 0; i < forecasts.length(); i++) {
                JSONObject forecastObject = forecasts.getJSONObject(i);

                String area = forecastObject.getString("area");

                if (area.equalsIgnoreCase("clementi")) {
                    String forecast = forecastObject.getString("forecast");
                    return "The weather in Clementi is" + forecast;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "No weather info";


    }
    String i = "";
    private String getMedicine() {
//        FirestoreHelper db = new FirestoreHelper();
//        List<Medicine> medList = db.getMedicineList();

        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            Log.e("logged in", "NOT logged in");
        } else{
            Log.e("logged in", "IS logged in Email: :" + FirebaseAuth.getInstance().getCurrentUser().getEmail() );
        }
        DisplayMedicine display = new DisplayMedicine();
        return "hi";

//        if (medList != null) {
//
//            for (Medicine medicine : medList) {
//                i += medicine.getMedName() + " ";
//            }
//            return "Your medicine are " + i;
//        } else{
//            return "You haven't added any medicine";
//        }





    }

    @Override
    protected void onStart() {
        super.onStart();
//        MainActivity r=this;
//        f=new Firestore(r);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(fAuthStateListener!=null){
            fFirebaseAuth.removeAuthStateListener(fAuthStateListener);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        int bufferSize = 3200;
        AudioRecord audioRecord = new AudioRecord(
                MediaRecorder.AudioSource.DEFAULT,
                16000,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize
        );
        if(shouldDetect) {
            audioRecord.stop();
            audioRecord.release();
            Log.d("hotword", "stop listening to hotword");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fFirebaseAuth.addAuthStateListener(fAuthStateListener);
        if(!shouldDetect) {
            startHotword();
        }
    }
}


