package com.example.dictionary_inz.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dictionary_inz.R
import com.example.dictionary_inz.ui.AppViewModelProvider
import com.example.dictionary_inz.ui.home.components.DictionaryTopAppBar
import com.example.dictionary_inz.ui.home.components.ErrorDialog
import com.example.dictionary_inz.ui.home.components.SearchBar
import com.example.dictionary_inz.ui.home.components.SearchHistoryHeader
import com.example.dictionary_inz.ui.home.components.WordList

@Composable
fun HomeView(
    onNavigateToItemDetails: (Int) -> Unit,
    onNavigateToLearnSelection: () -> Unit
) {
    val viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val textFieldFocusRequester = remember { FocusRequester() }

    Scaffold(
        topBar = { DictionaryTopAppBar(title = stringResource(R.string.home_title)) },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToLearnSelection) {
                Icon(
                    imageVector = Icons.Filled.School,
                    contentDescription = stringResource(R.string.home_fab_learn)
                )
            }
        }
    ) { innerPadding ->
        ErrorDialog(
            error = uiState.errorMessage,
            onDismiss = {
                viewModel.clearErrorMessage()
                textFieldFocusRequester.requestFocus()
        })

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            SearchBar(
                query = uiState.searchQuery,
                onQueryChange = viewModel::onSearchQueryChange,
                onSearchClick = {
                    viewModel.performSearch { foundWordId ->
                        onNavigateToItemDetails(foundWordId)
                    }
                    focusManager.clearFocus()
                },
                focusRequester = textFieldFocusRequester
            )

            Spacer(modifier = Modifier.height(24.dp))
            SearchHistoryHeader()
            WordList(
                uiState.wordList,
                onNavigateToItemDetails
            )
        }
    }
}