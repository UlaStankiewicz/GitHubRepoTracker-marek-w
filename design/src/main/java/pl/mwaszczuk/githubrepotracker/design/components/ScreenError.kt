package pl.mwaszczuk.githubrepotracker.design.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import pl.mwaszczuk.githubrepotracker.design.DesignDrawables
import pl.mwaszczuk.githubrepotracker.design.R
import pl.mwaszczuk.githubrepotracker.design.theme.*

@Composable
fun ScreenError(
    errorMessage: String,
    isRetryVisible: Boolean = true,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = SizeL),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(SizeXXL),
            painter = painterResource(DesignDrawables.ic_error),
            tint = LightBlue,
            contentDescription = ""
        )
        Text(
            modifier = Modifier
                .padding(top = SizeS),
            text = errorMessage,
            style = MaterialTheme.typography.h4,
            color = Black,
            textAlign = TextAlign.Center
        )

        if (isRetryVisible) {
            Button(
                modifier = Modifier
                    .padding(top = SizeL),
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Gray249,
                    contentColor = Black
                )
            ) {
                Text(
                    text = stringResource(R.string.try_again),
                    style = MaterialTheme.typography.h4
                )
            }
        }
    }
}
