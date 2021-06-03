package com.example.gitverificationbot;


import com.example.gitverificationbot.Model.*;
import com.example.gitverificationbot.Services.DatabaseService;
import com.example.gitverificationbot.Services.GithubClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class GitHubClientController {
    private final GithubClient githubService;
    private final DatabaseService databaseService;

    public GitHubClientController(GithubClient githubService, DatabaseService databaseService) {
        this.githubService = githubService;
        this.databaseService = databaseService;
    }

    @GetMapping("/repos")
    public List<Repository> getRepos() throws IOException {
        return githubService.getRepositories();
    }

    @GetMapping("/pulls/{owner}/{repo}")
    public List<PullRequest> getPulls(@PathVariable("owner") String owner,
                                      @PathVariable("repo") String repoName) throws IOException {
        return githubService.getPullRequests(owner, repoName);
    }

    @GetMapping("/pull/{owner}/{repo}/{pullNumber}")
    public List<PullInfo> getCommits(@PathVariable("owner") String owner,
                                     @PathVariable("repo") String repoName,
                                     @PathVariable("pullNumber") Integer pullNumber) throws IOException {
        return githubService.getPullRequest(owner, repoName, pullNumber);
    }

    @GetMapping("/{owner}/{repo}//issues")
    public List<Issue> getIssues(@PathVariable("owner") String owner,
                                 @PathVariable("repo") String repoName
    ) throws IOException {
        return githubService.getRepoIssues(owner, repoName);
    }

    @GetMapping("/pull/{owner}/{repo}/{pull_number}/comments")
    public List<ReviewComment> getRevComs(@PathVariable("owner") String owner,
                                          @PathVariable("repo") String repo,
                                          @PathVariable("pull_number") Integer pullNum) throws IOException {
        return githubService.getReview(owner, repo, pullNum);
    }

    @PostMapping("/repos")
    public Repository createRepo(@RequestBody Repository newRepo) throws IOException {
        return githubService.createRepository(newRepo);
    }

    @PostMapping("/pull/{owner}/{repo}/pulls/{pull_number}/comments")
    public ReviewComment createRevComm(@RequestBody ReviewComment newRevComm,
                                       @PathVariable("owner") String owner,
                                       @PathVariable("repo") String repo,
                                       @PathVariable("pull_number") Integer pullNum) throws IOException {
        return githubService.createRevComm(newRevComm, owner, repo, pullNum);
    }

    @PostMapping("/pull/{owner}/{repo}/pulls/{pull_number}/issuecomments")
    public Issue createIssueComment(@RequestBody Issue newIssueComm,
                                    @PathVariable("owner") String owner,
                                    @PathVariable("repo") String repo,
                                    @PathVariable("pull_number") Integer pullNum) throws IOException {
        return githubService.createIssueComm(newIssueComm, owner, repo, pullNum);
    }

    @GetMapping("/users")
    public List<Student> getUsers() {
        return databaseService.getStudents();
    }

    @DeleteMapping("/{owner}/{repo}/{comment_id}/deleteReviews")
    public String deleteReviews(@PathVariable("owner") String owner,
                                @PathVariable("repo") String repo,
                                @PathVariable("comment_id") Integer comment_id) throws IOException {
        return githubService.deleteReviews(owner, repo, comment_id);
    }

    @GetMapping("/pull/{owner}/{repo}/comments")
    public List<ReviewComment> getRevComsRepo(@PathVariable("owner") String owner,
                                 @PathVariable("repo") String repo) throws IOException {
        return githubService.getReviewRepo(owner, repo);
    }
}
