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
fun QuizActionRowResult(
    isAnswerCorrect: Boolean,
    onRepeat: () -> Unit,
    onProceed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (isAnswerCorrect) {
            Button(onClick = onRepeat) {
                Text(stringResource(R.string.quiz_repeat_question))
            }
            Button(onClick = onProceed) {
                Text(stringResource(R.string.quiz_mark_mastered))
            }
        } else {
            Button(onClick = onRepeat) {
                Text(stringResource(R.string.quiz_try_again))
            }
            Button(onClick = onProceed) {
                Text(stringResource(R.string.quiz_skip_question))
            }
        }
    }
}