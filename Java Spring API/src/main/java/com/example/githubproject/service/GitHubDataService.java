package com.example.githubproject.service;

import com.example.githubproject.database.GithubData;

import java.util.List;

public interface GitHubDataService {
    public GithubData addData(GithubData data);

    public List<GithubData> getRepos();
}
