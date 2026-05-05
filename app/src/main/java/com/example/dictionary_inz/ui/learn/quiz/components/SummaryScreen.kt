package com.example.dictionary_inz.ui.learn.quiz.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dictionary_inz.R

@Composable
fun SummaryScreen(
    correctAnswers: Int,
    totalQuestions: Int,
    onRepeatIncorrect: () -> Unit,
    onFinish: () -> Unit
) {
    val percentage = if (totalQuestions > 0) {
        (correctAnswers * 100) / totalQuestions
    } else {
        0
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.summary_session_finished),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.summary_mastered_questions, percentage),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(32.dp))
        if (percentage < 100 && totalQuestions > 0) {
            Button(
                onClick = onRepeatIncorrect,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.summary_review_questions))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        Button(
            onClick = onFinish,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.summary_finish_session))
        }
    }
}