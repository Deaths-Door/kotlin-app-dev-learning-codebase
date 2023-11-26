package com.deathsdoor.chillback.common.ui.components.lazylists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.ui.Modifier

@NoLiveLiterals
@Deprecated("FIND SOMETHING BETTER BUT HOPE IT WORKS, TO SOLVE NO VIRTUAL METHOD FOUND ISSUE")
@Composable
internal actual fun <T> LazyLists(
    modifier: Modifier,
    data: MutableList<T>,
    item: @Composable LazyItemScope.(T) -> Unit,
    useDefaultBehaviour: Boolean,
    showSortingChips: Boolean,
    sortingChips : @Composable () -> Unit,
    emptyListMessage: @Composable () -> Unit,
    nonDefaultBehaviourOnClick: (T) -> Unit
) {
    @Composable
    fun default(){
        if (data.isEmpty()) emptyListMessage()
        else LazyColumn(modifier = modifier.fillMaxWidth()){
            items(data, itemContent = item)
        }
    }

    if(!showSortingChips){
        default()
        return
    }

    Column {
        sortingChips()
        default()
    }
}

