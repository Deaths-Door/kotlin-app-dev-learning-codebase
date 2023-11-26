package com.deathsdoor.chillback.common.ui.screens.options

import android.content.ContentValues
import android.provider.MediaStore
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.push
import com.deathsdoor.astroplayer.core.dataclasses.MediaItem
import com.deathsdoor.chillback.common.data.database.Caching
import com.deathsdoor.chillback.common.data.music.RingTone
import com.deathsdoor.chillback.common.data.music.setAs
import com.deathsdoor.chillback.common.ui.components.image.AdvancedImage
import com.deathsdoor.chillback.common.ui.components.text.MarqueeText
import com.deathsdoor.chillback.common.ui.navigation.SongLongClickGraph
import com.deathsdoor.uri.isSourceFile
import io.github.xxfast.decompose.router.LocalRouter
import io.github.xxfast.decompose.router.Router
import java.io.File

//TODO check if backhandler is done auto by router
//TODO also optimze backstack correctly by dropping and pushing so Type.values().length - 1 == max backstack size
@Composable
internal actual fun ExtraSongOptions(mediaItem : MediaItem){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        /*AdvancedImage(
            image = mediaItem.metadata.artwork.toString(),
            contentDescription = "Song Image",
        )*/

        MarqueeText(
            text = mediaItem.metadata.title,
            textStyle = MaterialTheme.typography.headlineMedium,
        )

        MarqueeText(
            text = mediaItem.metadata.artist?.joinToString(", "){ it } ?: "No Given Artists",
            textStyle = MaterialTheme.typography.headlineMedium,
        )

        val options : List<Triple<String, ImageVector, () -> Unit>> = buildList {
            infix fun <T> MutableList<T>.add(it : T) = this@add.add(it)

            this add Triple("Share", Icons.Default.Share){}
            this add Triple("Add song to Album", Icons.Default.Share){}
            if(!mediaItem.media.isSourceFile) {
                this add Triple("Download Song for offline playing",Icons.Default.Share){}
                this add Triple("Report false metadata" ,Icons.Default.Share){}
            }

            else {
                val router = LocalRouter.current as Router<SongLongClickGraph>

                this add Triple("Open Lyric Editor",Icons.Default.Share){
                    router.push(SongLongClickGraph.LyricEditor)
                }
                this add Triple("Open Metadata Editor",Icons.Default.Share){
                    router.push(SongLongClickGraph.MetadataEditor)
                }

                this add Triple("Hide Local File from App",Icons.Default.Share){
                    // Caching.cacheLocalSongAdditionalInfo()
                }
                this add Triple("Delete Local File from device",Icons.Default.Share){
                    //TODO show msg if file is deleted also request permission if dont have it
                    File(mediaItem.media.toString()).delete()
                }

                val context = LocalContext.current
                this add Triple("Set as Ringtone",Icons.Default.Share){
                    //TODO maybe give them a better options to allow them to set a range of the song as ringtone
                    RingTone.setAs(context,mediaItem)
                }
            }
        }

        //TODO check if lazy list is needed
        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(options){
                Row(
                    modifier = Modifier
                        .clickable(onClick = it.third)
                        .padding(16.dp)
                ) {
                    Icon(imageVector = it.second,contentDescription = null)
                    MarqueeText(text = it.first)
                }
            }
        }
    }
}