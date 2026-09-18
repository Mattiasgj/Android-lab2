package com.example.androidlab2.model

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface FetchGitService {

    @Headers("Accept: application/vnd.github+json")
    @GET("repos/Mattiasgj/smart-home-gitops/issues/{issueNumber}/comments")
    suspend fun getPullRequestComments(
        @Path("issueNumber") issueNumber: Int,
        @Header("Authorization") token: String
    ): List<PullRequestComment>

    @Headers("Accept: application/vnd.github+json")
    @GET("repos/Mattiasgj/smart-home-gitops/pulls")
    suspend fun getPullRequests(
        @Query("state") state: String = "open",
        @Query("sort") sort: String = "created",
        @Query("direction") direction: String = "desc",
        @Query("per_page") perPage: Int = 1,
        @Header("Authorization") token: String
    ): List<PullRequest>
}