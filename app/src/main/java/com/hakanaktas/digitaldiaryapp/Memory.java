package com.hakanaktas.digitaldiaryapp;

import android.net.Uri;

public class Memory {
    public String title;
    public String content;
    public String id;
    public String uri;
    public String enlem;
    public String boylam;
    public String password;


    public Memory(String title, String content, String id, String uri, String enlem, String boylam,String password ) {
        this.title = title;
        this.content = content;
        this.id = id;
        this.uri = uri;
        this.enlem = enlem;
        this.boylam = boylam;
        this.password = password;

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
