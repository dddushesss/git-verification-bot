package com.example.gitverificationbot.Services;


import com.example.gitverificationbot.GithubApiInterface;
import com.example.gitverificationbot.Model.*;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

@Service
public class GithubClient {

    private final String accessToken;
    static final String API_BASE_URL = "https://api.github.com/";
    static final String API_VERSION_SPEC = "application/vnd.github.v3+json";
    static final String JSON_CONTENT_TYPE = "application/json";

    private final GithubApiInterface service;

    public GithubClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GithubApiInterface.class);

        this.accessToken = "token " + System.getenv("GitHubToken");
    }

    public List<Repository> getRepositories() throws IOException {
        Call<List<Repository>> retrofitCall = service.listRepos(accessToken, API_VERSION_SPEC);

        Response<List<Repository>> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

    public List<PullRequest> getPullRequests(String owner, String repoName) throws IOException {
        Call<List<PullRequest>> retrofitCall = service.listPulls(accessToken, API_VERSION_SPEC, repoName, owner);

        Response<List<PullRequest>> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

    public Repository createRepository(Repository repo) throws IOException {
        Call<Repository> retrofitCall = service.createRepo(repo, accessToken, API_VERSION_SPEC, JSON_CONTENT_TYPE);

        Response<Repository> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

    public List<PullInfo> getPullRequest(String owner, String repoName, Integer pullName) throws IOException {
        Call<List<PullInfo>> retrofitCall = service.listPullCommits(accessToken, API_VERSION_SPEC, repoName, owner, pullName);

        Response<List<PullInfo>> response = retrofitCall.execute();


        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

    public List<Issue> getRepoIssues(String owner, String repo) throws IOException {
        Call<List<Issue>> retrofitCall = service.listIssues(accessToken, API_VERSION_SPEC,repo, owner);

        Response<List<Issue>> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }


        return response.body();
    }

    public List<ReviewComment> getReview(String owner, String repo, Integer pullNum) throws IOException {
        Call<List<ReviewComment>> retrofitCall = service.listRevCom(accessToken, API_VERSION_SPEC, repo, owner, pullNum);

        Response<List<ReviewComment>> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }


        return response.body();
    }

    public String deleteReviews(String owner, String repo, Integer comment_id) throws IOException {
        Call<String> retrofitCall = service.deleteReviewComments(accessToken, API_VERSION_SPEC, repo, owner, comment_id);

        Response<String> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }


        return response.body();
    }

    public ReviewComment createRevComm(ReviewComment comment, String owner, String repo, Integer pullNum) throws IOException {
        Call<ReviewComment> retrofitCall = service.createRevComm(
                comment,
                accessToken,
                API_VERSION_SPEC,
                JSON_CONTENT_TYPE,
                repo, owner, pullNum);

        Response<ReviewComment> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

    public Issue createIssueComm(Issue comment, String owner, String repo, Integer pullNum) throws IOException {
        Call<Issue> retrofitCall = service.createIssueComm(
                comment,
                accessToken,
                API_VERSION_SPEC,
                JSON_CONTENT_TYPE,
                repo, owner, pullNum);

        Response<Issue> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

    public List<ReviewComment> getReviewRepo(String owner, String repo) throws IOException {
        Call<List<ReviewComment>> retrofitCall = service.listRevComRepo(accessToken, API_VERSION_SPEC, repo, owner);

        Response<List<ReviewComment>> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }


        return response.body();
    }
}