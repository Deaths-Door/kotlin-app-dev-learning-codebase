package com.deathsdoor.chillbackmusicplayer.ui.components.albumrecyclerview

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.deathsdoor.chillbackmusicplayer.R
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.Album

//TODO add async image from coil when it works to load images into app
@Composable
fun AlbumRecyclerViewItemVertical(data: Album, isPinned: Boolean = false, isDownloaded: Boolean = false, onClick: () -> Unit){
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        val (image, title, pinned, downloaded, artist, play) = createRefs()
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
           // painter = rememberCoilPainter(request = data.metadata.imageSource),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        Text(
            text = data.metadata.name,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(image.end)
                top.linkTo(image.top)
            }
        )
        if (isPinned) {
            Image(
                painter = painterResource(R.drawable.image_pin),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
                    .constrainAs(pinned) {
                        start.linkTo(title.end)
                        top.linkTo(title.bottom)
                    }
            )
        }
        if (isDownloaded) {
            Image(
                painter = painterResource(R.drawable.image_downloaded),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
                    .constrainAs(downloaded) {
                        start.linkTo(if (isPinned) pinned.end else title.end)
                        top.linkTo(title.bottom)
                    }
            )
        }
        Text(
            text = data.metadata.artist,
            fontSize = 14.sp,
            modifier = Modifier.constrainAs(artist) {
                start.linkTo(if (isDownloaded) downloaded.end else if (isPinned) pinned.end else title.end)
                top.linkTo(if (isDownloaded) downloaded.bottom else if (isPinned) pinned.bottom else title.bottom)
                bottom.linkTo(play.bottom)
            }
        )
        IconButton(
            onClick = { /* TODO: Play album */ },
            modifier = Modifier.constrainAs(play) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(artist.top)
            }
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = null
            )
        }
    }
}
