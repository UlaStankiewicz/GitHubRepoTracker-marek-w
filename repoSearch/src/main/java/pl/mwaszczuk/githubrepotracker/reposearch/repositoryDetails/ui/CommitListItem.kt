package pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import pl.mwaszczuk.githubrepotracker.design.DesignDrawables
import pl.mwaszczuk.githubrepotracker.design.theme.SizeL
import pl.mwaszczuk.githubrepotracker.design.theme.SizeS
import pl.mwaszczuk.githubrepotracker.design.theme.SizeXXS
import pl.mwaszczuk.githubrepotracker.design.theme.SizeXXXS
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.model.Commit

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommitListItem(
    item: Commit,
    isSelectModeOn: Boolean,
    onSelected: (Commit) -> Unit
) {
    Card(elevation = SizeXXXS) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = {
                        if (isSelectModeOn) {
                            onSelected(item.copy(isSelected = !item.isSelected))
                        }
                    },
                    onLongClick = {
                        onSelected(item.copy(isSelected = true))
                    }
                )
                .padding(SizeS),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(visible = isSelectModeOn) {
                Checkbox(
                    modifier = Modifier
                        .size(SizeL),
                    checked = item.isSelected,
                    onCheckedChange = { onSelected(item.copy(isSelected = it)) }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(SizeXXS)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        modifier = Modifier
                            .size(SizeS)
                            .padding(end = SizeXXXS),
                        painter = painterResource(DesignDrawables.ic_person),
                        contentDescription = "author"
                    )
                    Text(
                        text = item.authorName,
                        style = MaterialTheme.typography.body1
                    )
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        modifier = Modifier
                            .size(SizeS)
                            .padding(end = SizeXXXS),
                        painter = painterResource(DesignDrawables.ic_commit),
                        contentDescription = "commit"
                    )
                    Text(
                        text = item.sha,
                        style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onBackground)
                    )
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        modifier = Modifier
                            .size(SizeS)
                            .padding(end = SizeXXXS),
                        painter = painterResource(DesignDrawables.ic_comment),
                        contentDescription = "comment"
                    )
                    Text(
                        text = item.message,
                        style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onBackground)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ShiftItemPreview() {
    CommitListItem(
        item = Commit(
            sha = "sha",
            message = "message",
            authorName = "author"
        ),
        isSelectModeOn = false,
        onSelected = {}
    )
}
