package com.example.dictionary_inz.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dictionary_inz.R
import com.example.dictionary_inz.ui.AppViewModelProvider
import com.example.dictionary_inz.ui.details.components.ItemDetailsActionButtons
import com.example.dictionary_inz.ui.details.components.ItemDetailsCard
import com.example.dictionary_inz.ui.home.components.DictionaryTopAppBar

@Composable
fun ItemDetailsView(
    wordId: Int,
    onBackClick: () -> Unit,
    onWordDeleted: () -> Unit
) {
    val viewModel: ItemDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(wordId) {
        viewModel.loadWordById(wordId)
    }

    Scaffold(
        topBar = {
            DictionaryTopAppBar(
                title = stringResource(R.string.item_details_title),
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.word == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.item_details_loading_message),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                ItemDetailsCard(word = uiState.word!!)
                ItemDetailsActionButtons(
                    onBackClick = onBackClick,
                    onDelete = {
                        viewModel.deleteWord(uiState.word!!.id)
                        onWordDeleted()
                    }
                )
            }
        }
    }
}
