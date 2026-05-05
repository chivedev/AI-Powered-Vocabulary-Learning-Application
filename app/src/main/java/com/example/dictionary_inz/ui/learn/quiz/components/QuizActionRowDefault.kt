package com.example.dictionary_inz.ui.learn.quiz.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.dictionary_inz.R

@Composable
fun QuizActionRowDefault(
    onCheckAnswer: () -> Unit,
    onSkipQuestion: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = onCheckAnswer) {
            Text(stringResource(R.string.quiz_check_answer))
        }
        Button(onClick = onSkipQuestion) {
            Text(stringResource(R.string.quiz_skip_question))
        }
    }
}