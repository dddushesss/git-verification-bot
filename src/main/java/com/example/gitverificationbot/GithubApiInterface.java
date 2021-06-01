package com.example.gitverificationbot;



import com.example.gitverificationbot.Model.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface GithubApiInterface {
    @GET("user/repos")
    Call<List<Repository>> listRepos(@Header("Authorization") String accessToken,
                                     @Header("Accept") String apiVersionSpec);

    @GET("/repos/{owner}/{repo}/pulls/{pull_number}/commits")
    Call<List<PullInfo>> listPullCommits(@Header("Authorization") String accessToken,
                                         @Header("Accept") String apiVersionSpec,
                                         @Path("repo") String repo,
                                         @Path("owner") String owner,
                                         @Path("pull_number") Integer pullNumber);

    @GET("/repos/{owner}/{repo}/pulls")
    Call<List<PullRequest>> listPulls(@Header("Authorization") String accessToken,
                                      @Header("Accept") String apiVersionSpec,
                                      @Path("repo") String repo, @Path("owner") String owner);

    @GET("/repos/{owner}/{repo}/issues/comments")
    Call<List<Issue>> listIssues(@Header("Authorization") String accessToken,
                                 @Header("Accept") String apiVersionSpec,
                                 @Path("repo") String repo,
                                 @Path("owner") String owner);

    @GET("/repos/{owner}/{repo}/pulls/{pullNumber}/comments")
    Call<List<ReviewComment>> listRevCom(@Header("Authorization") String accessToken,
                                         @Header("Accept") String apiVersionSpec,
                                         @Path("repo") String repo,
                                         @Path("owner") String owner,
                                         @Path("pullNumber") Integer pullNum);

    @POST("user/repos")
    Call<Repository> createRepo(@Body Repository repo, @Header("Authorization") String accessToken,
                                @Header("Accept") String apiVersionSpec,
                                @Header("Content-Type") String contentType);

    @POST("/repos/{owner}/{repo}/pulls/{pullNumber}/comments")
    Call<ReviewComment> createRevComm(@Body ReviewComment reviewComment,
                                      @Header("Authorization") String accessToken,
                                      @Header("Accept") String apiVersionSpec,
                                      @Header("Content-Type") String contentType,
                                      @Path("repo") String repo,
                                      @Path("owner") String owner,
                                      @Path("pullNumber") Integer pullNum);

    @POST("/repos/{owner}/{repo}/issues/{issue_number}/comments")
    Call<Issue> createIssueComm(@Body Issue reviewComment,
                                      @Header("Authorization") String accessToken,
                                      @Header("Accept") String apiVersionSpec,
                                      @Header("Content-Type") String contentType,
                                      @Path("repo") String repo,
                                      @Path("owner") String owner,
                                      @Path("issue_number") Integer pullNum);



    @DELETE("/repos/{owner}/{repo}/issues/comments/{comment_id}")
    Call<String> deleteReviewComments(@Header("Authorization") String accessToken,
                                      @Header("Accept") String apiVersionSpec,
                                      @Path("repo") String repo,
                                      @Path("owner") String owner,
                                      @Path("comment_id") Integer comment_id);

    @GET("/repos/{owner}/{repo}/pulls/comments")
    Call<List<ReviewComment>> listRevComRepo(@Header("Authorization") String accessToken,
                                         @Header("Accept") String apiVersionSpec,
                                         @Path("repo") String repo,
                                         @Path("owner") String owner);
}
