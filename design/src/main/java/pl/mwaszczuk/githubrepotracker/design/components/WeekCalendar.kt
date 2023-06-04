package pl.mwaszczuk.githubrepotracker.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import pl.mwaszczuk.githubrepotracker.design.theme.*
import java.time.LocalDate

@Composable
fun WeekCalendar(
    days: List<LocalDate>,
    selectedDate: LocalDate,
    onDateClicked: (LocalDate) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(selectedDate) {
        val scrollIndex = days.indexOf(selectedDate) - 4
        if (scrollIndex >= 0) {
            listState.animateScrollToItem(scrollIndex)
        }
    }

    Card(
        shape = RectangleShape,
        elevation = SizeXXS
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .padding(vertical = SizeS),
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(SizeM, Alignment.CenterHorizontally),
            contentPadding = PaddingValues(horizontal = SizeS)
        ) {
            items(
                items = days,
                key = { it }
            ) {
                CalendarCell(
                    isSelected = it == selectedDate,
                    date = it,
                    onDateClicked = onDateClicked
                )
            }
        }
    }
}

@Composable
internal fun CalendarCell(
    isSelected: Boolean,
    date: LocalDate,
    onDateClicked: (LocalDate) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onDateClicked(date) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = date.month.name.take(3),
            style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onSurface)
        )
        Text(
            text = date.dayOfWeek.name.first().toString(),
            style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface)
        )
        Text(
            modifier = Modifier
                .size(SizeM)
                .padding(top = SizeXXXS)
                .let {
                    if (isSelected) {
                        it.background(MaterialTheme.colors.primary, CircleShape)
                    } else {
                        it
                    }
                },
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typography.body2.copy(
                color = if (!isSelected) {
                    MaterialTheme.colors.primary
                } else {
                    MaterialTheme.colors.surface
                }
            ),
            textAlign = TextAlign.Center
        )
        if (LocalDate.now() == date) {
            Divider(
                modifier = Modifier
                    .width(SizeS)
                    .padding(top = SizeXXS),
                thickness = SizeXXXS
            )
        }
    }
}
