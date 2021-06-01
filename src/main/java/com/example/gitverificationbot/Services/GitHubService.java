package com.example.gitverificationbot.Services;


import com.example.gitverificationbot.Model.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitHubService {
    private final GithubClient client;

    public GitHubService(GithubClient client) {
        this.client = client;

    }


    public List<Repository> getRepos() throws IOException {
        return client.getRepositories().stream()
                .map(Repository::getGitUrl)
                .map(x -> "Hello " + x)
                .map(x -> {
                    Repository repository = new Repository();
                    repository.setGitURL(x);
                    return repository;
                }).collect(Collectors.toList());

    }

    public List<PullRequest> getPulls() throws IOException{
        return client.getPullRequests("TestOwner","")
                .stream()
                .peek(x -> x.setUser(new User("Test User")))
                .collect(Collectors.toList());
    }

    public List<PullInfo> getPull() throws IOException{

        return client.getPullRequest("TestOwner","", 1)
                .stream()
                .peek(x -> x.setCommit(new Commit("Test commit")))
                .collect(Collectors.toList());

    }


}
