package com.example.gitverificationbot;


import com.example.gitverificationbot.Model.*;
import com.example.gitverificationbot.Services.GitHubService;
import com.example.gitverificationbot.Services.GithubClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;

public class GithubServiceTest extends AbstractTest {


    GitHubService service;
    GithubClient mock;


    @Before
    public void init() {
        mock = Mockito.mock(GithubClient.class, RETURNS_DEEP_STUBS);
        service = new GitHubService(mock);
    }

    @Test
    public void testGetRepos() throws IOException{

        Repository repository = new Repository();
        repository.setGitURL("Test Repo");
        when(mock.getRepositories()).thenReturn(Collections.singletonList(repository));
        List<Repository> repositories = service.getRepos();
        assertEquals("Hello Test Repo", repositories.get(0).url);
    }

    @Test
    public void testGetPulls() throws IOException{
        PullRequest pullRequest = new PullRequest();
        pullRequest.setUser(new User("Test User"));
        when(mock.getPullRequests("TestOwner", "")).thenReturn(Collections.singletonList(pullRequest));
        List<PullRequest> pullRequests = service.getPulls();
        assertEquals("Test User", pullRequests.get(0).getUser().getLogin());
    }

    @Test
    public void testGetPull() throws IOException{
        PullInfo pull = new PullInfo();
        pull.setCommit(new Commit("Test commit"));
        when(mock.getPullRequest("TestOwner", "",1)).thenReturn(Collections.singletonList(pull));
        List<PullInfo> pulls = service.getPull();
        assertEquals("Test commit", pulls.get(0).getCommit().getMessage());
    }

}
