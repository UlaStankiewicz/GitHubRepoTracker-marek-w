package pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import pl.mwaszczuk.githubrepotracker.design.theme.SizeS
import pl.mwaszczuk.githubrepotracker.reposearch.R

@Composable
fun RepoDetailsErrorBar(
    errorMessage: String,
    onRetry: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.error)
            .padding(horizontal = SizeS)
            .testTag("repository_details_error"),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = errorMessage,
            style = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.onBackground)
        )
        Button(
            onClick = onRetry
        ) {
            Text(
                text = stringResource(R.string.repository_details_retry)
            )
        }
    }
}
