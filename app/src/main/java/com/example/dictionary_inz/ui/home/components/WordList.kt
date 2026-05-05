package com.example.dictionary_inz.ui.home.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.dictionary_inz.R
import com.example.dictionary_inz.data.local.WordEntity

@Composable
fun WordList(wordList: List<WordEntity>, onItemClick: (Int) -> Unit) {
    if (wordList.isEmpty()) {
        Text(stringResource(R.string.word_list_empty_message))
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(wordList.reversed()) { word ->
                WordItem(
                    word = word,
                    onItemClick = { clickedWord -> onItemClick(clickedWord.id) }
                )
            }
        }
    }
}