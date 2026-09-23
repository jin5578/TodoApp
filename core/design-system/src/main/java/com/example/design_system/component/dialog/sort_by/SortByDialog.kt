package com.example.design_system.component.dialog.sort_by

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.design_system.component.CustomRadioButton
import com.example.design_system.theme.TodoTheme
import com.example.design_system.utils.getTitleResId
import com.example.model.SortByType
import com.example.design_system.R as DesignSystemR

@Composable
fun SortByDialog(
    sortByType: SortByType,
    onCloseClick: () -> Unit,
    onSelectClick: (SortByType) -> Unit,
) {
    var selectedSortByType by remember {
        mutableStateOf(value = sortByType)
    }

    Dialog(
        onDismissRequest = { onCloseClick() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(size = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(
                    horizontal = 2.dp,
                    vertical = 16.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = stringResource(id = DesignSystemR.string.tasks_sorted_by),
                    style = TodoTheme.typography.bold_20,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(height = 16.dp))

                SortByType.entries.forEach { sortByType ->
                    CustomRadioButton(
                        titleResId = sortByType.getTitleResId(),
                        isSelected = selectedSortByType == sortByType,
                        onClick = {
                            selectedSortByType = sortByType
                        }
                    )
                }

                Spacer(modifier = Modifier.height(height = 16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        modifier = Modifier.clickable {
                            onSelectClick(selectedSortByType)
                        }.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        ),
                        text = stringResource(id = DesignSystemR.string.select),
                        style = TodoTheme.typography.medium_16,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SortByDialogPreview() {
    TodoTheme {
        SortByDialog(
            sortByType = SortByType.DUE_DATE_AND_TIME,
            onCloseClick = {},
            onSelectClick = {}
        )
    }
}