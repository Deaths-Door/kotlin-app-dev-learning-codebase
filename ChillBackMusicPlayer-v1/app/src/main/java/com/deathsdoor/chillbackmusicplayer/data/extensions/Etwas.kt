package com.deathsdoor.chillbackmusicplayer.data.extensions

import java.util.regex.Pattern

fun String.isValidEmail(): Boolean = Pattern.compile("[a-zA-Z0-9+._%\\-]{1,256}@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+").matcher(this).matches()