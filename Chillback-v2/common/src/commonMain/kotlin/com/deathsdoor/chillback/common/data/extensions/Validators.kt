package com.deathsdoor.chillback.common.data.extensions


internal val emailRegex by lazy {  Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$") }
internal fun String.isValidEmail() = emailRegex.matches(this)