package com.kotlin.notebook_app;

import com.google.firebase.Timestamp;

public class Note {
    String title , content ;
    Timestamp timestamp ;

    public Note(){

    }
    public void setTitle(String title) {
        this.title = title;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }


}
