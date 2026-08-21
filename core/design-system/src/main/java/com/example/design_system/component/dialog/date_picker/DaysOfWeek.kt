package com.example.design_system.component.dialog.date_picker

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.theme.TodoTheme
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DaysOfWeek(
    modifier: Modifier = Modifier,
    daysOfWeek: List<DayOfWeek>,
    locale: Locale
) {
    Row(modifier = modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, locale),
                textAlign = TextAlign.Center,
                style = TodoTheme.typography.bold_14,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DaysOfWeekPreview() {
    TodoTheme {
        DaysOfWeek(daysOfWeek = DayOfWeek.entries, locale = Locale.KOREA)
    }
}