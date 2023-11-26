package com.deathsdoor.chillback.common.resources.image

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path


private var _imageVector : ImageVector? = null

val Icons.GoogleLogo : ImageVector get(){
    if(_imageVector != null) return _imageVector!!

    _imageVector = materialIcon(name = "GoogleLogo"){
        path(fill = SolidColor(Color(0xFF177FE6))){}
        /*path(fill = SolidColor(Color(0xFF177FE6)), stroke = SolidColor(Color.Black)) {
            moveTo(267.81f, 301.16f)
            curveTo(284.02f, 290.81f, 298.04f, 278.19f, 308.03f, 261.57f)
            curveTo(311.68f, 255.49f, 314.75f, 249.05f, 318.62f, 241.77f)
            curveTo(315.64f, 241.77f, 313.9f, 241.77f, 312.15f, 241.77f)
            curveTo(285.98f, 241.77f, 259.82f, 241.78f, 233.65f, 241.77f)
            curveTo(221.59f, 241.77f, 217.18f, 237.29f, 217.18f, 225.08f)
            curveTo(217.17f, 210.74f, 217.16f, 196.41f, 217.18f, 182.08f)
            curveTo(217.2f, 171.43f, 221.82f, 166.67f, 232.28f, 166.67f)
            curveTo(282.78f, 166.64f, 333.28f, 166.64f, 383.78f, 166.67f)
            curveTo(393.67f, 166.67f, 398.04f, 170.16f, 399.23f, 179.8f)
            curveTo(405.68f, 232.3f, 391.75f, 279.08f, 359.01f, 320.33f)
            curveTo(344.25f, 338.93f, 326.18f, 353.82f, 305.17f, 365.54f)
            curveTo(292.37f, 343.92f, 280.09f, 322.54f, 267.81f, 301.16f)
            close()
        }
        path(fill = SolidColor(Color(0xFFFF4C27)), stroke = SolidColor(Color(0x00000000))) {
            moveTo(43.03f, 104.64f)
            curveTo(51.2f, 93.19f, 58.26f, 80.72f, 67.73f, 70.47f)
            curveTo(105.41f, 29.65f, 152.45f, 8.94f, 208.54f, 8.55f)
            curveTo(209.0f, 33.7f, 209.01f, 58.32f, 209.01f, 82.94f)
            curveTo(174.04f, 83.89f, 144.51f, 96.85f, 121.27f, 123.21f)
            curveTo(116.47f, 128.65f, 112.6f, 134.92f, 107.71f, 140.92f)
            curveTo(99.04f, 136.43f, 90.97f, 131.82f, 82.89f, 127.23f)
            curveTo(69.61f, 119.69f, 56.32f, 112.17f, 43.03f, 104.64f)
            close()
        }
        path(fill = SolidColor(Color(0xFF13B348)), stroke = SolidColor(Color.Black)) {
            moveTo(107.66f, 258.63f)
            curveTo(116.43f, 272.13f, 126.59f, 284.36f, 139.67f, 293.95f)
            curveTo(160.04f, 308.89f, 182.91f, 316.4f, 208.56f, 317.46f)
            curveTo(209.0f, 342.61f, 209.01f, 367.22f, 209.01f, 391.83f)
            curveTo(166.48f, 391.91f, 128.21f, 379.0f, 94.16f, 353.7f)
            curveTo(73.21f, 338.14f, 56.07f, 319.06f, 43.65f, 295.71f)
            curveTo(65.27f, 282.99f, 86.47f, 270.81f, 107.66f, 258.63f)
            close()
        }
        path(fill = SolidColor(Color(0xFFFFD501)), stroke = SolidColor(Color.Black)) {
            moveTo(107.57f, 258.27f)
            curveTo(86.47f, 270.81f, 65.27f, 282.99f, 43.7f, 295.34f)
            curveTo(39.46f, 288.22f, 34.91f, 281.2f, 31.86f, 273.59f)
            curveTo(9.11f, 216.74f, 12.25f, 161.2f, 41.08f, 107.16f)
            curveTo(41.47f, 106.42f, 41.97f, 105.75f, 42.72f, 104.85f)
            curveTo(56.32f, 112.17f, 69.61f, 119.69f, 82.89f, 127.23f)
            curveTo(90.97f, 131.82f, 99.04f, 136.43f, 107.45f, 141.17f)
            curveTo(100.05f, 156.12f, 94.42f, 171.53f, 92.79f, 188.38f)
            curveTo(90.47f, 212.28f, 95.21f, 234.59f, 106.38f, 255.69f)
            curveTo(106.76f, 256.42f, 107.12f, 257.17f, 107.57f, 258.27f)
            close()
        }
        path(fill = SolidColor(Color(0xFFD94022)), stroke = SolidColor(Color.Black)) {
            moveTo(209.42f, 83.05f)
            curveTo(209.01f, 58.32f, 209.0f, 33.7f, 209.01f, 8.62f)
            curveTo(246.14f, 7.74f, 280.22f, 18.03f, 311.46f, 37.8f)
            curveTo(319.3f, 42.77f, 326.66f, 48.56f, 333.9f, 54.4f)
            curveTo(340.96f, 60.1f, 341.21f, 67.84f, 334.83f, 74.33f)
            curveTo(323.62f, 85.73f, 312.3f, 97.01f, 300.93f, 108.24f)
            curveTo(295.09f, 114.0f, 288.93f, 114.44f, 282.62f, 109.39f)
            curveTo(261.33f, 92.34f, 237.0f, 83.84f, 209.42f, 83.05f)
            close()
        }
        path(fill = SolidColor(Color(0xFF10993F)), stroke = SolidColor(Color.Black)) {
            moveTo(209.47f, 391.91f)
            curveTo(209.01f, 367.22f, 209.0f, 342.61f, 209.0f, 317.53f)
            curveTo(225.26f, 316.17f, 241.16f, 313.66f, 256.08f, 306.72f)
            curveTo(259.81f, 304.98f, 263.46f, 303.04f, 267.48f, 301.18f)
            curveTo(280.09f, 322.54f, 292.37f, 343.92f, 304.83f, 365.6f)
            curveTo(291.77f, 371.86f, 278.88f, 378.8f, 265.23f, 383.56f)
            curveTo(247.48f, 389.75f, 228.82f, 392.18f, 209.47f, 391.91f)
            close()
        }*/
    }
    return _imageVector!!
}