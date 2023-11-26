package com.deathsdoor.chillback.common.ui.components.chips

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun SortingSongChips(modifier: Modifier = Modifier, onCategorySelected : (category : SortingCategory) -> Unit){
    var currentChip by remember { mutableStateOf(-1) }
    FlowRow(modifier = modifier) {
        SortingCategory.values().forEachIndexed { index, it ->
            FilterChip(
                modifier = Modifier.padding(start = 8.dp),
                selected = currentChip == index,
                onClick = {
                    currentChip = index
                    onCategorySelected(it)
                },
                leadingIcon = {
                    Image(imageVector = Icons.Default.Person , contentDescription = null)
                },
                label = {
                    Text(it.displayName())
                }
            )
        }
    }
}