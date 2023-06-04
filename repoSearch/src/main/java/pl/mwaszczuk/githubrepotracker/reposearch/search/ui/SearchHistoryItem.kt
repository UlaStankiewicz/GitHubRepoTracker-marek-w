package pl.mwaszczuk.githubrepotracker.reposearch.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
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
import pl.mwaszczuk.githubrepotracker.design.theme.SizeXS
import pl.mwaszczuk.githubrepotracker.design.theme.SizeXXXS
import pl.mwaszczuk.githubrepotracker.reposearch.search.model.RepoSearchHistoryItem

@Composable
fun SearchHistoryItem(
    item: RepoSearchHistoryItem,
    onClick: (RepoSearchHistoryItem) -> Unit
) {
    Card(elevation = SizeXXXS) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick(item) }
                .padding(SizeS),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(SizeL),
                painter = painterResource(DesignDrawables.ic_github_mark),
                contentDescription = "github_logo"
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = SizeXS)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.body1
                )

                Text(
                    text = item.owner,
                    style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onBackground)
                )
            }
        }
    }
}

@Preview
@Composable
fun ShiftItemPreview() {
    SearchHistoryItem(
        item = RepoSearchHistoryItem(
            owner = "mwaszczuk",
            name = "githubrepotracker",
            id = 1
        ),
        onClick = {}
    )
}
