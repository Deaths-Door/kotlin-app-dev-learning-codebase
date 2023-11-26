package com.example.musiclistener

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.fragment.app.Fragment
import com.example.musiclistener.CommonFunctions.mapToList
import com.example.musiclistener.Constants.SETTING_SPRACHE
import com.example.musiclistener.music.MusicViewModel
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

object CommonFunctions {
    inline fun <T, U> List<T>.filterLists(uList: List<U>, filterPredicate: (T, U) -> Boolean) = filter { m -> uList.any { filterPredicate(m, it)} }
    //TODO improve this so can do any types
    inline fun runDiffCodeDiffTypes(data: Any, string:() -> Unit, int:() -> Unit, boolean:() -> Unit) {
        when(data){ is Int -> int(); is String -> string(); is Boolean -> boolean() } }
    fun <K,V> HashMap<K,V>.mapToList(): ArrayList<Any> {
        val list = arrayListOf<Any>()
        this.values.forEach{ if(it != null) list.add(it)}
        return list
    }
    fun Context.canonicalFileDir(): File = this.filesDir.canonicalFile
    fun Activity.setLanguage(vm: MusicViewModel){
        val locale = Locale(vm.userSettings[SETTING_SPRACHE].toString());
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        this.baseContext.resources.updateConfiguration(config,this.baseContext.resources.displayMetrics)
    }
    private fun isDarkModeOn(context:Context): Boolean = (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    fun Fragment.setBackgroundAsTheme() =
        if(isDarkModeOn(requireContext())) this.requireView().setBackgroundColor(requireContext().getColor(R.color.light_gray))
        else this.requireView().setBackgroundColor(requireContext().getColor(R.color.pale_skin))
    fun List<LocalSong>.convertToListSong(): List<Song> {
        val data = arrayListOf<Song>()
        this.forEach {
            data.add(it.convertToSongType())
        }
        return data
    }
}