package com.pedro.vlctestapp;

import java.io.Serializable;

public class DetObj implements Serializable {
    private String name;
    private int Id;
    private boolean chosen;

    public DetObj(String name, int Id) {
        this.name = name;
        this.Id = Id;
        this.chosen = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return Id;
    }

    public boolean getchosen() {
        return chosen;
    }

    public void setChosen() {
        chosen = !chosen;
    }

    public String toString() {
        return name;
    }
}

