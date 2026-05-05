package com.example.dictionary_inz.ui.learn.quiz.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dictionary_inz.R
import com.example.dictionary_inz.ui.learn.quiz.QuizDifficulty
import com.example.dictionary_inz.ui.learn.quiz.QuizQuestion

@Composable
fun QuizQuestionCard(
    quizQuestion: QuizQuestion,
    isAnswerCorrect: Boolean?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (quizQuestion.difficulty == QuizDifficulty.HARD) {
                Text(
                    stringResource(R.string.quiz_translate_to_english),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = quizQuestion.wordEntity.polishSentence,
                    style = MaterialTheme.typography.headlineMedium
                )
            } else {
                Text(
                    text= stringResource(R.string.quiz_complete_sentence),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = quizQuestion.displayedSentence,
                    style = MaterialTheme.typography.headlineMedium,
                    color = if (isAnswerCorrect == true)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = quizQuestion.wordEntity.polishSentence,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}