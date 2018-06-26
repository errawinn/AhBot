package com.example.clair.ahbot;

import java.util.List;

public class MedicalProfile {
    private String id;
    private String age;
    private List<String> allergies;
    private List<String> diseases;
    private String name;

    public MedicalProfile(String id,String name,String age,List<String> allergies, List<String> diseases){
        this.id = id;
        this.name = name;
        this.age = age;
        this.allergies = allergies;
        this.diseases = diseases;
    }
    public MedicalProfile(){

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public List<String> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<String> diseases) {
        this.diseases = diseases;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
