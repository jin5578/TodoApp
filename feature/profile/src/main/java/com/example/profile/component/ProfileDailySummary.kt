package com.example.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.design_system.theme.TodoTheme
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLineComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisTickComponent
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModel
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.compose.cartesian.data.ColumnCartesianLayerModel
import com.patrykandpatrick.vico.compose.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.Fill
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.example.design_system.R as DesignSystemR

private val DailyChartWeekDays = listOf(
    DayOfWeek.SUNDAY,
    DayOfWeek.MONDAY,
    DayOfWeek.TUESDAY,
    DayOfWeek.WEDNESDAY,
    DayOfWeek.THURSDAY,
    DayOfWeek.FRIDAY,
    DayOfWeek.SATURDAY,
)


@Composable
internal fun ProfileDailySummary(
    modifier: Modifier = Modifier,
    dailyEntries: ImmutableList<Float>,
    fromDate: LocalDate,
    toDate: LocalDate,
    locale: Locale,
    onDailyDateRangeChanged: (fromDate: LocalDate, toDate: LocalDate) -> Unit,
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(id = DesignSystemR.string.daily_completed),
                style = TodoTheme.typography.medium_12,
                color = MaterialTheme.colorScheme.onBackground
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 4.dp)
            ) {
                Icon(
                    modifier = Modifier.size(size = 12.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            val newToDate = fromDate.minusDays(1)
                            val newFromDate = newToDate.minusDays(6)
                            onDailyDateRangeChanged(newFromDate, newToDate)
                        },
                    imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_arrow_left_small),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )

                val dateFormat = DateTimeFormatter.ofPattern("M/dd")
                Text(
                    text = "${fromDate.format(dateFormat)}" +
                            "-" +
                            "${toDate.format(dateFormat)}",
                    style = TodoTheme.typography.medium_12,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Icon(
                    modifier = Modifier.size(size = 12.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            val newFromDate = toDate.plusDays(1)
                            val newToDate = newFromDate.plusDays(6)
                            onDailyDateRangeChanged(newFromDate, newToDate)
                        },
                    imageVector = ImageVector.vectorResource(id = DesignSystemR.drawable.svg_arrow_right_small),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        ProfileDailyChart(
            dailyEntries = dailyEntries,
            locale = locale,
        )
    }
}

@Composable
private fun ProfileDailyChart(
    modifier: Modifier = Modifier,
    dailyEntries: ImmutableList<Float>,
    locale: Locale,
) {
    val labelColor = MaterialTheme.colorScheme.onBackground
    val lineColor = MaterialTheme.colorScheme.outlineVariant
    val barColor = MaterialTheme.colorScheme.primary

    val axisLabel = rememberAxisLabelComponent(
        style = TextStyle(color = labelColor, fontSize = 10.sp)
    )
    val axisLine = rememberAxisLineComponent(
        fill = Fill(color = lineColor)
    )
    val axisTick = rememberAxisTickComponent(
        fill = Fill(color = lineColor)
    )

    val startAxis = VerticalAxis.rememberStart(
        label = axisLabel,
        line = axisLine,
        tick = null,
        guideline = null,
        itemPlacer = remember { VerticalAxis.ItemPlacer.step(step = { 4.0 }) },
    )
    val bottomAxis = HorizontalAxis.rememberBottom(
        label = axisLabel,
        line = axisLine,
        tick = null,
        guideline = null,
        valueFormatter = remember(key1 = locale) {
            CartesianValueFormatter { _, value, _ ->
                DailyChartWeekDays[value.toInt()].getDisplayName(
                    java.time.format.TextStyle.SHORT,
                    locale
                )
            }
        },
    )

    val columnLayer = rememberColumnCartesianLayer(
        columnProvider = ColumnCartesianLayer.ColumnProvider.series(
            columns = arrayOf(
                rememberLineComponent(
                    fill = Fill(color = barColor),
                    thickness = 8.dp,
                    shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
                )
            )
        ),
        rangeProvider = remember {
            CartesianLayerRangeProvider.fixed(
                minY = 0.0,
                maxY = 16.0
            )
        },
    )

    val chart = rememberCartesianChart(
        layers = arrayOf(columnLayer),
        startAxis = startAxis,
        bottomAxis = bottomAxis,
    )

    val model = remember(key1 = dailyEntries) {
        CartesianChartModel(
            models =
                arrayOf(ColumnCartesianLayerModel.build {
                    series(
                        y = dailyEntries
                    )
                })
        )
    }

    CartesianChartHost(
        modifier = modifier.fillMaxWidth().height(height = 160.dp),
        chart = chart,
        model = model,
    )
}

@Preview(showBackground = true)
@Composable
private fun ProfileDailySummaryPreview() {
    TodoTheme {
        val dailyEntries =
            persistentListOf(3f, 7f, 5f, 12f, 8f, 2f, 6f)
        val today = LocalDate.now()
        ProfileDailySummary(
            dailyEntries = dailyEntries,
            fromDate = today.minusDays(6),
            toDate = today,
            locale = Locale.KOREA,
            onDailyDateRangeChanged = { _, _ -> }
        )
    }
}