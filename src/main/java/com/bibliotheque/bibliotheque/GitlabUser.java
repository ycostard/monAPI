package com.bibliotheque.bibliotheque;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitlabUser {
    
    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("username")
    private String username;
    
    @JsonProperty("state")
    private String state;

    @JsonProperty("avatar_url")
    private String avatar_url;

    @JsonProperty("web_url")
    private String web_url;
}
