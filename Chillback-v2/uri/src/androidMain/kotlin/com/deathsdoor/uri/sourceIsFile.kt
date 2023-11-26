package com.deathsdoor.uri

import android.net.Uri
import java.io.File

actual val URI.isSourceFile: Boolean get() = uri.startsWith("file://")
