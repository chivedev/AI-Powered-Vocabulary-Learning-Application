package com.example.dictionary_inz.ui.learn.flashcards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dictionary_inz.data.local.WordEntity

@Composable
fun FlashcardCard(
    currentWord: WordEntity,
    rotation: Float,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(250.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable { onCardClick() }
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 8 * density
            },
        contentAlignment = Alignment.Center
    ) {
        if (rotation <= 90f) {
            Text(
                text = currentWord.englishWord,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center,
                modifier = Modifier.graphicsLayer { rotationY = 0f }
            )
        } else {
            Text(
                text = currentWord.polishTranslation,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center,
                modifier = Modifier.graphicsLayer { rotationY = 180f }
            )
        }
    }
}