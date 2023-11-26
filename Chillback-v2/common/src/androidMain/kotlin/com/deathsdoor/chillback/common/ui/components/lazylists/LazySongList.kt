package com.deathsdoor.chillback.common.ui.components.lazylists

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.deathsdoor.astroplayer.core.common.addMediaItem
import com.deathsdoor.astroplayer.core.dataclasses.MediaItem
import com.deathsdoor.chillback.common.ui.components.chips.SortingSongChips
import com.deathsdoor.chillback.common.ui.components.lazylists.items.LazySongListVerticalItem
import com.deathsdoor.chillback.common.ui.navigation.SongLongClickGraph
import com.deathsdoor.chillback.common.ui.providers.GlobalAstroPlayerState
import eu.wewox.modalsheet.ExperimentalSheetApi
import eu.wewox.modalsheet.ModalSheet
import io.github.xxfast.decompose.router.content.RoutedContent
import io.github.xxfast.decompose.router.rememberRouter

@OptIn(ExperimentalSheetApi::class)
@Composable
internal actual fun LazySongList(
    modifier: Modifier,
    mediaItems: MutableList<MediaItem>,
    showSortingChips: Boolean,
    useDefaultBehaviour: Boolean,
    emptyListMessage: @Composable () -> Unit,
    nonDefaultBehaviourOnClick : (mediaItem : MediaItem) -> Unit
) {
    var selectedMediaItem : MediaItem? by remember { mutableStateOf(null) }

    LazyLists(
        modifier = modifier,
        data = mediaItems,
        item = {
            if(useDefaultBehaviour){
                val astroPlayer = GlobalAstroPlayerState.current.astroPlayer
                LazySongListVerticalItem(
                    mediaItem = it,
//                    useDefaultBehaviour = true,
                    onClick = {
                        astroPlayer.addMediaItem(it)
                        astroPlayer.play()
                    },
                    onLongClick = {
                        selectedMediaItem = it
                    },
                    onLikeStatusChange = { isLiked -> /*TODO handle isLiked Update*/ }
                )
            }
            else {
                LazySongListVerticalItem(
                    mediaItem = it,
                    useDefaultBehaviour = false,
                    onClick = { nonDefaultBehaviourOnClick(it) },
                )
            }
        },
        useDefaultBehaviour = useDefaultBehaviour,
        showSortingChips = showSortingChips,
        sortingChips = {
            SortingSongChips(onCategorySelected = {
                val result = it.sort(mediaItems)
                mediaItems.clear()
                mediaItems.addAll(result)
            })
        },
        emptyListMessage = emptyListMessage,
        nonDefaultBehaviourOnClick = nonDefaultBehaviourOnClick
    )

    if(!useDefaultBehaviour) return

    val nestedRouter = rememberRouter(SongLongClickGraph::class,listOf(SongLongClickGraph.ExtraSongOptions))

    ModalSheet(
        data = selectedMediaItem,
        onDataChange = {
            selectedMediaItem = it
        },
        content = {
            RoutedContent(
                router = nestedRouter,
                content = {
                    selectedMediaItem?.let { it1 -> it.content(it1) }
                }
            )
        }
    )

    /*val default = default@ @Composable {

        if (mediaItems.isEmpty()) {
            emptyListMessage()
            return@default
        }

        var selectedMediaItem : MediaItem? by remember { mutableStateOf(null) }

        LazyColumn(modifier.fillMaxWidth()) {
            items(mediaItems) {
                if(useDefaultBehaviour){
                    val astroPlayer = GlobalAstroPlayerState.current.astroPlayer
                    LazySongListVerticalItem(
                        mediaItem = it,
                        useDefaultBehaviour = true,
                        onClick = {
                            astroPlayer.addMediaItem(it)
                            astroPlayer.play()
                        },
                        onLongClick = {
                            selectedMediaItem = it
                        },
                        onLikeStatusChange = { isLiked -> /*TODO handle isLiked Update*/ }
                    )
                }
                else {
                    LazySongListVerticalItem(
                        mediaItem = it,
                        useDefaultBehaviour = false,
                        onClick = { nonDefaultBehaviourOnClick(it) },
                    )
                }
            }
        }

        if(!useDefaultBehaviour) return@default

        val nestedRouter = rememberRouter(SongLongClickGraph::class,listOf(SongLongClickGraph.ExtraSongOptions))

        ModalSheet(
            data = selectedMediaItem,
            onDataChange = {},
            content = {
                RoutedContent(
                    router = nestedRouter,
                    content = {
                        it.content(mediaItems)
                    }
                )
            }
        )
    }

    if(!showSortingChips){
        default()
        return
    }

    Column {
        SortingSongChips {
            val result = it.sort(mediaItems)
            mediaItems.clear()
            mediaItems.addAll(result)
        }
        default()
    }*/
}