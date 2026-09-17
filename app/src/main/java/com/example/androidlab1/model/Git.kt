package com.example.androidlab1.model

data class PullRequestComment(
    val id: Long,
    val body: String,
    val user: User
)

data class User(
    val login: String
)

data class PullRequest(
    val id: Long,
    val number: Int,
)