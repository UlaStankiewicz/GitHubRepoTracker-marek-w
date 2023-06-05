package pl.mwaszczuk.githubrepotracker.reposearch.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import pl.mwaszczuk.githubrepotracker.design.DesignDrawables
import pl.mwaszczuk.githubrepotracker.design.components.AppBarHeight
import pl.mwaszczuk.githubrepotracker.design.components.CustomOutlinedTextField
import pl.mwaszczuk.githubrepotracker.design.theme.*
import pl.mwaszczuk.githubrepotracker.reposearch.R

@Composable
fun SearchTopBar(
    repoOwnerInput: String,
    repoNameInput: String,
    onSearchInputChanged: (String, String) -> Unit,
    onSearchClicked: (String, String) -> Unit
) {
    TopAppBar(
        modifier = Modifier.requiredHeight(AppBarHeight),
        backgroundColor = MaterialTheme.colors.surface,
        contentPadding = PaddingValues(horizontal = SizeS),
        elevation = SizeXXXS
    ) {
        CustomOutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(top = SizeXS)
                .padding(bottom = SizeXXS)
                .align(Alignment.CenterVertically)
                .testTag("input_owner"),
            value = repoOwnerInput,
            onValueChange = { onSearchInputChanged(it, repoNameInput) },
            textStyle = MaterialTheme.typography.h5,
            singleLine = true,
            label = {
                Text(
                    text = stringResource(R.string.search_owner_label),
                    style = MaterialTheme.typography.caption
                )
            }
        )

        Spacer(modifier = Modifier.width(SizeXS))

        CustomOutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(top = SizeXS)
                .padding(bottom = SizeXXS)
                .align(Alignment.CenterVertically)
                .testTag("input_name"),
            value = repoNameInput,
            onValueChange = { onSearchInputChanged(repoOwnerInput, it) },
            textStyle = MaterialTheme.typography.h5,
            singleLine = true,
            label = {
                Text(
                    text = stringResource(R.string.search_name_label),
                    style = MaterialTheme.typography.caption
                )
            }
        )

        Icon(
            modifier = Modifier
                .padding(start = SizeXXS)
                .requiredSize(SizeM)
                .clickable { onSearchClicked(repoOwnerInput, repoNameInput) }
                .align(Alignment.CenterVertically),
            painter = painterResource(
                id = DesignDrawables.ic_search
            ),
            contentDescription = "search"
        )
    }
}

@Preview
@Composable
fun SearchTopBarPreview() {
    GitHubRepoTrackerTheme {
        SearchTopBar(
            repoOwnerInput = "mwaszczuk",
            repoNameInput = "githubrepotracker",
            onSearchInputChanged = { _, _ -> },
            onSearchClicked = { _, _ -> }
        )
    }
}
