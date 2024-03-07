package com.example.gitrepo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepoPopularity {
    private long id;
    @JsonProperty("node_id")
    private String nodeId;
    private String name;
    @JsonProperty("full_name")
    private String fullName;
    private boolean isPrivate;
    private RepoUser owner;
    private boolean fork;
    private String url;
    @JsonProperty("forks_url")
    private String forksUrl;
    @JsonProperty("stargazers_url")
    private String stargazersUrl;
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
    @JsonProperty("pushed_at")
    private Date pushedAt;
    @JsonProperty("git_url")
    private String gitUrl;
    @JsonProperty("ssh_url")
    private String sshUrl;
    @JsonProperty("clone_url")
    private String cloneUrl;
    private String homepage;
    @JsonProperty("stargazers_count")
    private int stargazersCount;
    @JsonProperty("watchers_count")
    private int watchersCount;
    @JsonProperty("forks_count")
    private int forksCount;
    private boolean disabled;
    private int forks;
    private RepoUser organization;
    @JsonProperty("network_count")
    private int networkCount;
    @JsonProperty("subscribers_count")
    private int subscribersCount;
}
