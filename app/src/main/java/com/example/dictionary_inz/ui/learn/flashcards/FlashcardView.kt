package com.example.dictionary_inz.ui.learn.flashcards

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dictionary_inz.R
import com.example.dictionary_inz.ui.AppViewModelProvider
import com.example.dictionary_inz.ui.home.components.DictionaryTopAppBar
import com.example.dictionary_inz.ui.learn.flashcards.components.FlashcardActionRow
import com.example.dictionary_inz.ui.learn.flashcards.components.FlashcardCard
import com.example.dictionary_inz.ui.learn.flashcards.components.FlashcardSummaryScreen

@Composable
fun FlashcardView(
    onBackClick: () -> Unit,
    onReturnHome: () -> Unit
) {
    val viewModel: FlashcardViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val uiState by viewModel.uiState.collectAsState()

    val rotation by animateFloatAsState(
        targetValue = if (uiState.isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 400)
    )

    Scaffold(
        topBar = {
            DictionaryTopAppBar(
                title = stringResource(R.string.flashcard_title),
                showBackButton = true,
                onBackClick = onBackClick
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onReturnHome) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(R.string.return_home)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val currentWord = uiState.currentWord
            if (currentWord != null) {
                FlashcardCard(
                    currentWord = currentWord,
                    rotation = rotation,
                    onCardClick = { viewModel.flipCard() }
                )
                Spacer(modifier = Modifier.height(24.dp))
                FlashcardActionRow(
                    onUserKnowsWord = { viewModel.onUserKnowsWord() },
                    onUserWantsRepeat = { viewModel.onUserWantsRepeat() }
                )
            } else {
                FlashcardSummaryScreen(
                    onRestart = { viewModel.startNewSession() },
                    onReturnHome = onReturnHome
                )
            }
        }
    }
}