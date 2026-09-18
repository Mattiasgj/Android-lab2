package com.example.androidlab2

import com.example.androidlab2.model.DeceptionDetector
import com.example.androidlab2.model.RetrofitClient
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class DeceptionDetectorTest {

    @Test
    fun fetchCommentsAndValidate() = runTest {

        val apiKey = BuildConfig.API_KEY
        val detector = DeceptionDetector()

        // Fetch comments from PR #1
        val comments = RetrofitClient.fetchGitService.getPullRequestComments(
            issueNumber = 1,
            token = "Bearer $apiKey"
        )

        // Validate every comment
        for (comment in comments) {

            val score = detector.validatePullComments(comment.body)

            println("Comment: ${comment.body}")
            println("Confidence score: $score")
        }

        // Make sure we actually received comments
        assertTrue(comments.isNotEmpty())
    }
}