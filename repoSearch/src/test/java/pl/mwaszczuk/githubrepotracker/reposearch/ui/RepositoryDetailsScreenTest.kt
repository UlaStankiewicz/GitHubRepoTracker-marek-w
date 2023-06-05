package pl.mwaszczuk.githubrepotracker.reposearch.ui

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import pl.mwaszczuk.githubrepotracker.design.theme.GitHubRepoTrackerTheme
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.model.Commit
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.ui.RepositoryDetailsScreenLayout

@RunWith(RobolectricTestRunner::class)
// workaround for https://github.com/robolectric/robolectric/issues/6593
@Config(instrumentedPackages = ["androidx.loader.content"])
class RepositoryDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testTopBarTitle() {
        composeTestRule.setContent {
            GitHubRepoTrackerTheme {
                RepositoryDetailsScreenLayout(
                    commits = emptyList(),
                    isSelectModeOn = false,
                    errorMessage = null,
                    repoName = "repoName",
                    repoOwner = "repoOwner",
                    onItemSelected = { },
                    onShareClicked = { },
                    onCloseSelectMode = { },
                    onRetry = { }
                )
            }
        }
        composeTestRule.onNodeWithTag("topbar_title")
            .assertExists()
            .assertTextContains("repoOwner/repoName")
    }

    @Test
    fun testCommitsDisplay() {
        composeTestRule.setContent {
            GitHubRepoTrackerTheme {
                RepositoryDetailsScreenLayout(
                    commits = listOf(
                        Commit("sha1", "message1", "author1"),
                        Commit("sha2", "message2", "author2")
                    ),
                    isSelectModeOn = false,
                    errorMessage = null,
                    repoName = "repoName",
                    repoOwner = "repoOwner",
                    onItemSelected = { },
                    onShareClicked = { },
                    onCloseSelectMode = { },
                    onRetry = { }
                )
            }
        }
        composeTestRule.onNodeWithText("message1").assertExists()
        composeTestRule.onNodeWithText("author1").assertExists()
        composeTestRule.onNodeWithText("sha1").assertExists()
        composeTestRule.onNodeWithText("message2").assertExists()
        composeTestRule.onNodeWithText("author2").assertExists()
        composeTestRule.onNodeWithText("sha2").assertExists()
    }

    @Test
    fun testSelectionMode() {
        composeTestRule.setContent {
            GitHubRepoTrackerTheme {
                RepositoryDetailsScreenLayout(
                    commits = listOf(
                        Commit("sha1", "message1", "author1", true),
                        Commit("sha2", "message2", "author2")
                    ),
                    isSelectModeOn = true,
                    errorMessage = null,
                    repoName = "repoName",
                    repoOwner = "repoOwner",
                    onItemSelected = { },
                    onShareClicked = { },
                    onCloseSelectMode = { },
                    onRetry = { }
                )
            }
        }
        composeTestRule.onNodeWithContentDescription("share").assertExists()
        composeTestRule.onNodeWithContentDescription("close").assertExists()
        composeTestRule.onAllNodesWithTag("commit_checkbox").assertCountEquals(2)
    }

    @Test
    fun testErrorMessageVisibility() {
        val errorMessage = "errorMessage"
        composeTestRule.setContent {
            GitHubRepoTrackerTheme {
                RepositoryDetailsScreenLayout(
                    commits = listOf(
                        Commit("sha1", "message1", "author1", true),
                        Commit("sha2", "message2", "author2")
                    ),
                    isSelectModeOn = true,
                    errorMessage = errorMessage,
                    repoName = "repoName",
                    repoOwner = "repoOwner",
                    onItemSelected = { },
                    onShareClicked = { },
                    onCloseSelectMode = { },
                    onRetry = { }
                )
            }
        }
        composeTestRule.onNodeWithTag("repository_details_error").assertExists()
        composeTestRule.onNodeWithText(errorMessage).assertExists()
    }
}
