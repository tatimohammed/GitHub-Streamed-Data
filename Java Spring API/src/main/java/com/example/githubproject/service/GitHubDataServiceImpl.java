package com.example.githubproject.service;

import com.example.githubproject.dao.ReposRepository;
import com.example.githubproject.database.GithubData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GitHubDataServiceImpl implements GitHubDataService{

    @Autowired
    private ReposRepository repository;

    @Override
    public GithubData addData(GithubData data) {
        // TODO Auto-generated method stub
        return repository.insert(data);
    }

    @Override
    public List<GithubData> getRepos() {
        // TODO Auto-generated method stub
        return repository.findAllWithSelectedFields();
    }

}