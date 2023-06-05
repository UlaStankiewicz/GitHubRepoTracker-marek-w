package pl.mwaszczuk.githubrepotracker.reposearch.extensions

import android.content.Context
import android.content.Intent
import pl.mwaszczuk.githubrepotracker.reposearch.repositoryDetails.model.Commit

fun Context.shareCommits(commits: List<Commit>, repoName: String, repoOwner: String) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    val commitsString = buildString {
        append("Commits for $repoName/$repoOwner\n\n")
        commits.forEach {
            append(it.toString())
        }
    }
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Commits from repository: $repoName by $repoOwner")
    shareIntent.putExtra(Intent.EXTRA_TEXT, commitsString)

    val chooserIntent = Intent.createChooser(shareIntent, "Share via")

    if (shareIntent.resolveActivity(packageManager) != null) {
        startActivity(chooserIntent)
    }
}
