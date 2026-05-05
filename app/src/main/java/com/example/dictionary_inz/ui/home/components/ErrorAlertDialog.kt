package com.example.dictionary_inz.ui.home.components

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dictionary_inz.R
import com.example.dictionary_inz.data.repository.WordRepositoryError
import com.example.dictionary_inz.util.toUiText

@Composable
fun ErrorDialog(
    error: WordRepositoryError?,
    onDismiss: () -> Unit
) {
    if (error == null) return

    AlertDialog(
        modifier = Modifier.border(
            width = 2.dp,
            color = MaterialTheme.colorScheme.onErrorContainer,
            shape = RoundedCornerShape(24.dp)
        ),
        onDismissRequest = { onDismiss() },
        icon = {
            Icon(
                imageVector = Icons.Filled.Error,
                contentDescription = stringResource(R.string.error_icon_description),
                tint = MaterialTheme.colorScheme.error
            )
        },
        title = {
            Text(
                text = stringResource(R.string.error_dialog_title),
                color = MaterialTheme.colorScheme.onErrorContainer,
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = error.toUiText().asString(),
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = when (error) {
                            is WordRepositoryError.NoInternet -> stringResource(R.string.errorButton_no_internet)
                            is WordRepositoryError.ServerError -> stringResource(R.string.errorButton_server_error)
                            is WordRepositoryError.WordNotFound -> stringResource(R.string.errorButton_word_not_found)
                            is WordRepositoryError.UnknownError -> stringResource(R.string.errorButton_unknown)
                            is WordRepositoryError.SingleWordOnlyError -> stringResource(R.string.errorButton_single_word_only)
                        },
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        shape = RoundedCornerShape(24.dp),
        containerColor = MaterialTheme.colorScheme.errorContainer
    )
}