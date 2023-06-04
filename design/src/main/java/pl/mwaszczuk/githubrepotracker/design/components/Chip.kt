package pl.mwaszczuk.githubrepotracker.design.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.mwaszczuk.githubrepotracker.design.DesignDrawables
import pl.mwaszczuk.githubrepotracker.design.theme.SizeXS
import pl.mwaszczuk.githubrepotracker.design.theme.SizeXXXS

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    text: String = "",
    icon: Painter? = null,
    color: Color = MaterialTheme.colors.primary
) {
    Row(
        modifier = modifier
            .border(1.dp, color, RoundedCornerShape(SizeXXXS))
            .padding(horizontal = SizeXXXS),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Icon(
                modifier = Modifier
                    .size(SizeXS),
                painter = icon,
                contentDescription = "",
                tint = color
            )
        }
        Text(
            modifier = Modifier
                .padding(start = SizeXXXS),
            text = text,
            style = MaterialTheme.typography.caption.copy(color = color)
        )
    }

}

@Preview
@Composable
fun ChipPreview() {
    Chip(
        text = "Chip",
        icon = painterResource(DesignDrawables.ic_arrow_back)
    )
}
