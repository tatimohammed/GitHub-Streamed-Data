package com.example.githubproject.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class GithubData {
    @Id
    private String StreamId;

    private Object jsondata;

    public String getStreamId() {
        return StreamId;
    }

    public void setStreamId(String streamId) {
        StreamId = streamId;
    }

    public Object getJsondata() {
        return jsondata;
    }

    public void setJsondata(Object jsondata) {
        this.jsondata = jsondata;
    }
}
