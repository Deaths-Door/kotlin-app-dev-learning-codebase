package com.deathsdoor.chillbackmusicplayer.data.extensions

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.*
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.deathsdoor.chillbackmusicplayer.R
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.formatMinuteSecondMilliSecond
import java.io.File
import java.net.URL
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt


//TODO delete files from extensions the ones that are decumishied
object Extensions {
    fun Float.int() = this.roundToInt()
    @Deprecated("there is a default method for this")
    fun Long.toMinutes(): Long = TimeUnit.MILLISECONDS.toMinutes(this)
    @Deprecated("there is a default method for this")
    fun Long.toSeconds(): Int = (TimeUnit.MILLISECONDS.toSeconds(this) % 60).toInt()
    @Deprecated("there is a default method for this")
    fun Long.toMilliSeconds(): Int = (TimeUnit.MILLISECONDS.toSeconds(this) % 1000).toInt()
    @Deprecated("there is a default method for this")
    fun Long.toMilliSecondsBetweenZeroHundred(): Int = ((TimeUnit.MILLISECONDS.toSeconds(this) % 60) * 1000 % 100).toInt()
    @Deprecated("there is a default method for this")
    fun Long.formatMinuteSecondMilliSecond(): String = "${this.toMinutes()}:${this.toSeconds()}:${this.toMilliSecondsBetweenZeroHundred()}"
    @Deprecated("there is a default method for this")
    fun Int.minuteToMilliseconds(): Long = TimeUnit.MINUTES.toMinutes(this.toLong())
    @Deprecated("there is a default method for this")
    fun Int.secondToMilliseconds(): Long = TimeUnit.MINUTES.toSeconds(this.toLong())

    fun Context.showToast(s:String,duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this,s,duration).show()
    fun String.isValidURL(): Boolean
        = try { URL(this).toURI();true }
          catch (e: Exception) { false }
    fun String.isValidPath(): Boolean {
        val file = File(this)
        return file.exists() && file.canRead()
    }
    fun String.asURI() = Uri.parse(this)
    fun File.asURI() = Uri.fromFile(this)

    //TODO use this
    fun <T> MutableLiveData<List<T>>.addToList(newData:T){
        val x =(this.value as ArrayList?)
        x?.add(newData)
        this.postValue(x)
    }

    fun Context.deleteFile(uri:Uri) = DocumentFile.fromSingleUri(this,uri)?.delete()
    fun Context.deleteFile(file:File) = deleteFile(Uri.fromFile(file))
    fun Context.deleteFile(path:String) = deleteFile(Uri.fromFile(File(path)))

    inline fun wait(delay:Long, crossinline action:() -> Unit){
        Handler(Looper.getMainLooper()).postDelayed({ action() }, delay)
    }

    fun Context.color(@ColorRes color:Int) = ContextCompat.getColor(this,color)
    fun View.resetColor() = this.setBackgroundResource(android.R.color.transparent)
    fun Fragment.changeStatusBarColor(@ColorRes color:Int) {
        val window = this.requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = requireContext().color(color)
    }

    inline fun Context.showAlertDialogBox(title: String, message: String,positiveButtonText:String,negativeButtonText:String,crossinline action:(isSuccess:Boolean,dialog:DialogInterface) ->Unit){
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton(negativeButtonText) { dialog, _ -> action(false,dialog) }
            .setPositiveButton(positiveButtonText) { dialog, _ -> action(true,dialog) }
            .show()
    }

    inline fun <T : ViewBinding> Context.showAlertDialogBox(bindingInflater: (LayoutInflater) -> T): Pair<T, AlertDialog> {
        val binding = bindingInflater(LayoutInflater.from(this))
        val window =  AlertDialog.Builder(this)
            .setView(binding.root)
            .show()
        return Pair(binding,window)
    }

    fun View.margins(start: Int = marginStart, top: Int = marginTop, end: Int = marginEnd, bottom: Int = marginBottom) {
        val layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(start, top, end, bottom)
        this.layoutParams = layoutParams
    }
}

