package com.example.androidlab1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidlab1.BuildConfig
import com.example.androidlab1.model.DeceptionDetector
import com.example.androidlab1.model.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.first
import kotlin.time.Duration.Companion.milliseconds

class MainViewModel : ViewModel() {

    private val detector = DeceptionDetector()

    private val _uiState =
        MutableStateFlow<SecurityUiState>(SecurityUiState.Normal)

    val uiState: StateFlow<SecurityUiState> =
        _uiState.asStateFlow()

    init {
        startPolling()
    }

    private fun startPolling() {

        viewModelScope.launch(Dispatchers.IO) {

            while (isActive) {

                try {
                    val pullRequests = RetrofitClient.fetchGitService.getPullRequests(
                        token = "Bearer ${BuildConfig.API_KEY}"
                    )

                    val prNumber = pullRequests.first().number

                    val comments =
                        RetrofitClient.fetchGitService.getPullRequestComments(
                            issueNumber = prNumber,
                            token = "Bearer ${BuildConfig.API_KEY}"
                        )

                    for (comment in comments) {

                        val score = withContext(Dispatchers.Default) {
                            detector.validatePullComments(comment.body)
                        }

                        println("Comment: ${comment.body}")
                        println("Score: $score")

                        if (score >= 60) {

                            _uiState.value =
                                SecurityUiState.SecurityAlert(
                                    confidenceScore = score,
                                    rawComment = comment.body
                                )

                        } else {

                            _uiState.value =
                                SecurityUiState.Normal
                        }
                    }

                } catch (e: Exception) {

                    println("Polling error: ${e.message}")
                }

                delay(30_000.milliseconds)
            }
        }
    }
}