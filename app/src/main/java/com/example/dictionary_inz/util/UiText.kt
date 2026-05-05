package com.example.dictionary_inz.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    data class StringResource(
        @StringRes val resId: Int,
        val args: List<Any> = emptyList()
    ) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is StringResource -> stringResource(id = resId, *args.toTypedArray())
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is StringResource -> context.getString(resId, *args.toTypedArray())
        }
    }
}