package com.example.dictionary_inz.ui.learn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dictionary_inz.R
import com.example.dictionary_inz.ui.AppViewModelProvider
import com.example.dictionary_inz.ui.home.components.DictionaryTopAppBar

@Composable
fun LearnSelectionView(
    onNavigateToQuiz: () -> Unit,
    onNavigateToFlashcards: () -> Unit,
    onBackClick: () -> Unit,
    onReturnHome: () -> Unit
) {
    val viewModel: LearnSelectionViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            DictionaryTopAppBar(
                title = stringResource(R.string.learn_selection_title),
                showBackButton = true,
                onBackClick = onBackClick
            )
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
            if (uiState.isDatabaseEmpty) {
                Text(
                    text = stringResource(R.string.learn_selection_empty_database_message),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onReturnHome,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.learn_selection_return_home))
                }
            } else {
                Button(
                    onClick = onNavigateToQuiz,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(stringResource(R.string.learn_selection_quiz_button))
                }
                Button(
                    onClick = onNavigateToFlashcards,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(stringResource(R.string.learn_selection_flashcards_button))
                }
            }
        }
    }
}