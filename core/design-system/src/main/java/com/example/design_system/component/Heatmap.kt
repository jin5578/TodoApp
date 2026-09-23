package com.example.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.model.HeatmapEntry
import com.example.design_system.theme.TodoTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.Locale

private const val DAYS_IN_WEEK = 7
private const val MAX_LEVEL = 4
private val CellSize = 12.dp
private val CellSpacing = 3.dp
private val WeekDaysOrderedFromSunday = listOf(
    DayOfWeek.SUNDAY,
    DayOfWeek.MONDAY,
    DayOfWeek.TUESDAY,
    DayOfWeek.WEDNESDAY,
    DayOfWeek.THURSDAY,
    DayOfWeek.FRIDAY,
    DayOfWeek.SATURDAY,
)

@Composable
fun Heatmap(
    modifier: Modifier = Modifier,
    entries: List<HeatmapEntry>,
    locale: Locale,
    endDate: LocalDate = LocalDate.now(),
    weekCount: Int = 53,
) {
    /*
        특정 날짜의 레벨을 0(1)로 조회할 수 있도록 List<HeatmapEntry>를 Map<LocalDate, Int>로 변환
     */
    val levelByDate = remember(key1 = entries) {
        entries.associate { it.date to it.level }
    }

    /*
        remember로 감싼 이유는 entries/endDate/weekCount가 바뀌지 않는 한 재구성(recomposition) 때마다 이 계산을 반복하지 않기 위함.
     */
    val weeks = remember(key1 = endDate, key2 = weekCount) {
        /*
            endDate(기본 오늘)가 속한 주의 일요일(lastWeekStart)을 구하고, 거시서 weekCount - 1주만큼 거슬러 올라가 firstWeekStart를 구함.
        */
        val lastWeekStart =
            endDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
        val firstWeekStart = lastWeekStart.minusWeeks((weekCount - 1).toLong())

        /*
            이후 주 단위로 weekCount개((기본 53주 ≈ 1년치)의 List<LocalDate>(각 7일)를 만들어 weeks: List<List<LocalDate>>를 완성
            즉, weeks[weekIndex][dayIndex]가 하나의 날짜
        */
        (0 until weekCount).map { weekIndex ->
            val weekStart = firstWeekStart.plusWeeks(weekIndex.toLong())
            (0 until DAYS_IN_WEEK).map { dayIndex ->
                weekStart.plusDays(dayIndex.toLong())
            }
        }
    }

    /*
        초기 진입 시 가장 최근 주(오른쪽 끝)가 보이도록 리스트 상태의 시작 인덱스를 마지막 주로 지정.
     */
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = (weeks.size - 1).coerceAtLeast(minimumValue = 0)
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = CellSpacing)
    ) {
        DaysOfWeekLabels(locale = locale)

        LazyRow(
            modifier = Modifier.weight(weight = 1f),
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(space = CellSpacing)
        ) {
            itemsIndexed(items = weeks) { weekIndex, week ->
                val monthLabel = week.first().takeIf { date ->
                    weekIndex == 0 || date.month != weeks[weekIndex - 1].first().month
                }?.month?.getDisplayName(TextStyle.SHORT, locale).orEmpty()

                Column(verticalArrangement = Arrangement.spacedBy(space = CellSpacing)) {
                    Text(
                        text = monthLabel,
                        style = TodoTheme.typography.regular_08,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1
                    )

                    week.forEach { date ->
                        HeatmapCell(
                            level = if (date.isAfter(endDate)) null else levelByDate[date]
                                ?: 0
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DaysOfWeekLabels(locale: Locale) {
    Column(verticalArrangement = Arrangement.spacedBy(space = CellSpacing)) {
        Text(
            text = "",
            style = TodoTheme.typography.regular_08,
            maxLines = 1
        )

        WeekDaysOrderedFromSunday.forEach { dayOfWeek ->
            Box(
                modifier = Modifier.height(height = CellSize),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = dayOfWeek.getDisplayName(
                        TextStyle.SHORT,
                        locale
                    ),
                    style = TodoTheme.typography.regular_08,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun HeatmapCell(level: Int?) {
    val color = when {
        level == null -> Color.Transparent
        level <= 0 -> MaterialTheme.colorScheme.surfaceContainerHighest
        else -> MaterialTheme.colorScheme.primary.copy(
            alpha = level.coerceAtMost(maximumValue = MAX_LEVEL) / MAX_LEVEL.toFloat()
        )
    }

    Box(
        modifier = Modifier
            .size(size = CellSize)
            .clip(shape = RoundedCornerShape(size = 2.dp))
            .background(color = color)
    )
}

@Preview(showBackground = true)
@Composable
private fun HeatmapPreview() {
    val today = LocalDate.now()
    val entries = (0..365).mapNotNull { offset ->
        val date = today.minusDays(offset.toLong())
        val level = listOf(0, 0, 1, 2, 3, 4).random()
        if (level == 0) null else HeatmapEntry(date = date, level = level)
    }

    TodoTheme {
        Heatmap(
            entries = entries,
            locale = Locale.KOREA
        )
    }
}