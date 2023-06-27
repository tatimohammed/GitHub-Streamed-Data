package com.example.githubproject.dao;

import com.example.githubproject.database.GithubData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReposRepository extends MongoRepository<GithubData, String> {

    @Query(value = "{}", fields = "{'jsondata.name': 1, 'jsondata.stargazers_count': 1, 'jsondata.watchers_count': 1, 'jsondata.language': 1, 'jsondata.forks': 1, 'jsondata.created_at': 1}")
    List<GithubData> findAllWithSelectedFields();
}