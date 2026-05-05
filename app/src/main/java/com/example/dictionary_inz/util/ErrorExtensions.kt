package com.example.dictionary_inz.util

import com.example.dictionary_inz.R
import com.example.dictionary_inz.data.repository.WordRepositoryError

fun WordRepositoryError.toUiText(): UiText.StringResource = when (this) {
    is WordRepositoryError.NoInternet -> UiText.StringResource(R.string.error_no_internet)
    is WordRepositoryError.ServerError -> UiText.StringResource(R.string.error_server_error)
    is WordRepositoryError.WordNotFound -> UiText.StringResource(R.string.error_word_not_found, listOf(word))
    is WordRepositoryError.UnknownError -> UiText.StringResource(R.string.error_unknown)
    is WordRepositoryError.SingleWordOnlyError -> UiText.StringResource(R.string.error_single_word_only)
}