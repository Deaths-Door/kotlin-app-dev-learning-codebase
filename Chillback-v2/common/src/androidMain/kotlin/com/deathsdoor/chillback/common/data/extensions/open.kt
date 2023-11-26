package com.deathsdoor.chillback.common.data.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.deathsdoor.chillback.common.data.settings.Type


internal fun Type.OpenWebsite.open(context : Context) = startActivity(
    context,
    Intent(Intent.ACTION_VIEW, Uri.parse(url)),
    Bundle()
)
