package com.deathsdoor.chillbackmusicplayer.data.extensions

import android.annotation.SuppressLint
import android.util.Log

 fun <T> String.debugLog(msg:T) = Log.d(this,msg.toString())
fun <T> String.errorLog(msg:T) = Log.e(this,msg.toString())
fun <T> String.warningLog(msg:T) = Log.w(this,msg.toString())
fun <T> String.verboseLog(msg:T) = Log.v(this,msg.toString())