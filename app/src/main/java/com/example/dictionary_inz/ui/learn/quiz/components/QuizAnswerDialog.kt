package com.example.dictionary_inz.ui.learn.quiz.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.dictionary_inz.R
import com.example.dictionary_inz.ui.learn.quiz.QuizDifficulty
import com.example.dictionary_inz.ui.learn.quiz.QuizQuestion

fun buildAnnotatedSentence(
    originalWords: List<String>,
    missingIndex: Int
): AnnotatedString {
    return buildAnnotatedString {
        originalWords.forEachIndexed { index, word ->
            if (index == missingIndex) {
                withStyle(style = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)) {
                    append(word)
                }
            } else {
                append(word)
            }
            if (index != originalWords.lastIndex) {
                append(" ")
            }
        }
    }
}

@Composable
fun QuizAnswerDialog(
    quizQuestion: QuizQuestion,
    userAnswer: String,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    val displayText: AnnotatedString = when {
        quizQuestion.difficulty == QuizDifficulty.EASY && quizQuestion.missingIndex != null -> {
            buildAnnotatedSentence(quizQuestion.originalWords, quizQuestion.missingIndex)
        }
        else -> {
            AnnotatedString(quizQuestion.wordEntity.englishSentence)
        }
    }
    AlertDialog(
        onDismissRequest = { },
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = 128.dp,
                bottom = 256.dp,
                start = 8.dp,
                end = 8.dp
            ),
        title = null,
        text = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.quiz_correct_answer),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = displayText,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                if (userAnswer.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = stringResource(R.string.quiz_user_answer),
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = userAnswer,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = onConfirm) {
                    Text(stringResource(R.string.quiz_continue))
                }
            }
        },
        confirmButton = {}
    )
}