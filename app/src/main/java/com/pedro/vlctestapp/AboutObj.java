package com.pedro.vlctestapp;

import java.io.Serializable;

public class AboutObj implements Serializable {
    private String name;
    private String about;


    public AboutObj(String name, String about) {
        this.name = name;
        this.about = about;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

}
