package com.deathsdoor.chillback.common.ui.components.lazylists

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.deathsdoor.chillback.common.data.music.Album


@Composable
internal expect fun <T> LazyLists(
    modifier: Modifier,
    data: MutableList<T>,
    item : @Composable LazyItemScope.(T) -> Unit,
    useDefaultBehaviour: Boolean,
    showSortingChips: Boolean,
    sortingChips : @Composable () -> Unit,
    emptyListMessage: @Composable () -> Unit,
    nonDefaultBehaviourOnClick : (T) -> Unit,
)