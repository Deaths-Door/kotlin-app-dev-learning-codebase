package com.deathsdoor.chillbackmusicplayer.ui.navigation.root

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainScope
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.deathsdoor.chillbackmusicplayer.R
import com.deathsdoor.chillbackmusicplayer.data.appdatabase.AppDataBase
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.Album
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.Metadata
import com.deathsdoor.chillbackmusicplayer.data.extensions.CoroutineHilfer
import com.deathsdoor.chillbackmusicplayer.data.permissions.PermissionHandler
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.AlbumRecyclerViewViewModel
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.updateValue
import com.deathsdoor.chillbackmusicplayer.ui.components.albumrecyclerview.AlbumRecyclerView
import com.deathsdoor.chillbackmusicplayer.ui.theme.Farben

//TODO add searchLibrary and newPlaylist to the layout dont know if i want it in the first place
//TODO finish the layout
@Composable
fun UserLibrary(navController: NavController) {
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (userProfileFoto, sectionTitle, lieblingSongs, todo, externalStorageSongs, bar,recyclerView) = createRefs()
        //TODO update this with firebase user image
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "UserProfileFoto",
            modifier = Modifier
                .constrainAs(userProfileFoto) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
                .padding(start = 12.dp, top = 16.dp)
                .size(40.dp)
        )

        //TODO change to bold as fontstyle
        Text(
            modifier = Modifier
                .constrainAs(sectionTitle) {
                    bottom.linkTo(userProfileFoto.bottom)
                    start.linkTo(userProfileFoto.end)
                    top.linkTo(userProfileFoto.top)
                }
                .padding(start = 16.dp),
            text = "My Beats",
            fontSize = 30.sp,
            fontStyle = FontStyle.Normal
        )
        //TODO add app:layout_constraintWidth_percent="0.3"
        //        app:layout_constraintHeight_percent="0.2"
        // to the lambda by using fillmaxwidth/height %
        //TODO add image to it
        val _userLibraryCard: @Composable (id: ConstrainedLayoutReference, contentDescription: String, constraintBlock: ConstrainScope.() -> Unit,onClick:() -> Unit) -> Unit =
            { id, contentDescription, constraintBlock,onClick ->
                Card(
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(width = 1.dp, color = Farben.Orange),
                    modifier = Modifier
                        .constrainAs(id) { constraintBlock() }
                        .padding(start = 6.dp, top = 36.dp)
                        .clickable { onClick() }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = contentDescription,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .size(90.dp)
                    )
                }
            }

        _userLibraryCard(
            lieblingSongs, "Liebling Songs Image",
            constraintBlock={
                start.linkTo(parent.start)
                top.linkTo(userProfileFoto.bottom)
            },
            onClick={

            }
        )
        _userLibraryCard(
            todo, "Todo idk",
            constraintBlock={
                end.linkTo(externalStorageSongs.start)
                start.linkTo(lieblingSongs.end)
                top.linkTo(lieblingSongs.top)
            },
            onClick={

            }
        )
        var clicked by remember { mutableStateOf(false) }
        _userLibraryCard(
            externalStorageSongs, "ExternalStorageSongs",
            constraintBlock={
                end.linkTo(parent.end)
                start.linkTo(todo.end)
                top.linkTo(todo.top)
            },
            onClick={
                clicked = true
            }
        )
        if(clicked) PermissionHandler.RequestPermission(permission = PermissionHandler.Permission.READ_EXTERNAL_STORAGE, onResult = {
            Log.d("UserLibrary","Permission granted is $it")
        })


        //TODO bar to add , search,sort user playlists
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .padding(15.dp)
            .constrainAs(bar) {
                top.linkTo(lieblingSongs.bottom)
            }
        ) {
            IconButton(onClick = { /*TODO add sort logic */ }) {
                Icon(Icons.Default.Add, "Add")
            }
        }
        val context = LocalContext.current
        val userAlbumDao = remember { AppDataBase.database(context).userAlbumDao }
        val albumRecyclerViewViewModel = remember { AlbumRecyclerViewViewModel() }
        LaunchedEffect(Unit) {
            albumRecyclerViewViewModel.displayedAlbums.updateValue(CoroutineHilfer.onBackgroundThread { userAlbumDao.getAlle() }
                ?: emptyList())
        }
        albumRecyclerViewViewModel.displayedAlbums.updateValue(
            listOf(
                Album(metadata = Metadata(name = "I")),
                Album(metadata = Metadata(name = "II")),
                Album(metadata = Metadata(name = "III"))
            )
        )
        //TODO add orientaiton based on something
        //TODO add constraint to it
        Box(modifier = Modifier.constrainAs(recyclerView){ top.linkTo(bar.bottom) }){
            AlbumRecyclerView(viewmodel = albumRecyclerViewViewModel)
        }
    }
}