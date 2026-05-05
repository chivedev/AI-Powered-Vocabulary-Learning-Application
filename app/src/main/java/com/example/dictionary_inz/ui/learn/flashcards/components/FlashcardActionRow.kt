package com.example.dictionary_inz.ui.learn.flashcards.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dictionary_inz.R

@Composable
fun FlashcardActionRow(
    onUserKnowsWord: () -> Unit,
    onUserWantsRepeat: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Button(
            onClick = onUserKnowsWord,
            modifier = Modifier.weight(1f)
        ) {
            Text(stringResource(R.string.flashcards_known))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = onUserWantsRepeat,
            modifier = Modifier.weight(1f)
        ) {
            Text(stringResource(R.string.flashcards_repeat))
        }
    }
}