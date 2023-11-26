package com.deathsdoor.chillback.common.ui.components.lazylists

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.router.stack.push
import com.deathsdoor.chillback.common.data.music.Album
import com.deathsdoor.chillback.common.ui.components.chips.SortingSongChips
import com.deathsdoor.chillback.common.ui.components.lazylists.items.LazyAlbumListVerticalItem
import com.deathsdoor.chillback.common.ui.navigation.Other
import com.deathsdoor.chillback.common.ui.navigation.Route
import com.deathsdoor.chillback.common.ui.providers.GlobalViewModel
import eu.wewox.modalsheet.ExperimentalSheetApi
import eu.wewox.modalsheet.ModalSheet
import io.github.xxfast.decompose.router.LocalRouter
import io.github.xxfast.decompose.router.Router

@OptIn(ExperimentalSheetApi::class)
@Composable
internal actual fun LazyAlbumList(
    modifier: Modifier,
    albums: MutableList<Album>,
    useDefaultBehaviour: Boolean,
    showSortingChips: Boolean,
    emptyListMessage: @Composable () -> Unit,
    nonDefaultBehaviourOnClick : (album : Album) -> Unit
) {
    var selectedAlbum : Album? by remember { mutableStateOf(null) }

    LazyLists(
        modifier = modifier,
        data = albums,
        item = {
            if(useDefaultBehaviour){
                val router = LocalRouter.current as Router<Route>
                val viewmodel = GlobalViewModel.current
                LazyAlbumListVerticalItem(
                    album = it,
                    useDefaultBehaviour = true,
                    onClick = {
                        viewmodel.seletedAlbum = it
                        router.push(Other.DisplayAlbumDetails)
                    },
                    onLongClick = {  selectedAlbum = it  },
                )
            }
            else {
                LazyAlbumListVerticalItem(
                    album = it,
                    useDefaultBehaviour = false,
                    onClick = { nonDefaultBehaviourOnClick(it) }
                )
            }
        },
        useDefaultBehaviour = useDefaultBehaviour,
        showSortingChips = showSortingChips,
        sortingChips = {
            SortingSongChips(onCategorySelected = {
                //TODO
            })
        },
        emptyListMessage = emptyListMessage,
        nonDefaultBehaviourOnClick = nonDefaultBehaviourOnClick
    )

    if(!useDefaultBehaviour) return

    ModalSheet(
        data = selectedAlbum,
        onDataChange = {},
        content = {
            //TODO IDK WHAT TO ADD HERE
        }
    )

    /*val default = default@ @Composable {

        if (albums.isEmpty()) {
            emptyListMessage()
            return@default
        }

        var selectedAlbum : Album? by remember { mutableStateOf(null) }

        LazyColumn(modifier.fillMaxWidth()) {
            items(albums) {
                if(useDefaultBehaviour){
                    val router = LocalRouter.current as Router<Route>
                    val viewmodel = GlobalViewModel.current
                    LazyAlbumListVerticalItem(
                        album = it,
                        useDefaultBehaviour = true,
                        onClick = {
                            viewmodel.seletedAlbum = it
                            router.push(Other.DisplayAlbumDetails)
                        },
                        onLongClick = { selectedAlbum = it },
                    )
                }
                else {
                    LazyAlbumListVerticalItem(
                        album = it,
                        useDefaultBehaviour = false,
                        onClick = { nonDefaultBehaviourOnClick(it) }
                    )
                }
            }
        }

        if(!useDefaultBehaviour) return@default

        ModalSheet(
            data = selectedAlbum,
            onDataChange = {},
            content = {
                //TODO IDK WHAT TO ADD HERE
            }
        )
    }

    if(!showSortingChips){
        default()
        return
    }

    Column {
        SortingSongChips {
          //  val result = it.sort(mediaItems)
           // mediaItems.clear()
           // mediaItems.addAll(result)
        }
        default()
    }*/
}