package com.example.androidlab1.model

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface FetchGitService {

    @Headers("Accept: application/vnd.github+json")
    @GET("repos/Mattiasgj/smart-home-gitops/issues/{issueNumber}/comments")
    suspend fun getPullRequestComments(
        @Path("issueNumber") issueNumber: Int,
        @Header("Authorization") token: String
    ): List<PullRequestComment>
}