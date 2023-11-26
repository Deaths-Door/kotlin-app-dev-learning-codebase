package com.deathsdoor.chillback.common.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.deathsdoor.chillback.common.data.firebase.currentTheme
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.serialization.Serializable

internal object Theme {
    @Serializable
    @Immutable
    internal enum class Values {
        OrangeLight {
            override val colors: ColorScheme = ColorScheme(
                primary=Color(0xFFB12D00),
                onPrimary=Color(0xFFFFFFFF),
                primaryContainer=Color(0xFFFFDBD1),
                onPrimaryContainer=Color(0xFF3B0900),
                secondary=Color(0xFF77574E),
                onSecondary=Color(0xFFFFFFFF),
                secondaryContainer=Color(0xFFFFDBD1),
                onSecondaryContainer=Color(0xFF2C150F),
                tertiary=Color(0xFF6C5D2F),
                onTertiary=Color(0xFFFFFFFF),
                tertiaryContainer=Color(0xFFF6E1A6),
                onTertiaryContainer=Color(0xFF231B00),
                error=Color(0xFFBA1A1A),
                errorContainer=Color(0xFFFFDAD6),
                onError=Color(0xFFFFFFFF),
                onErrorContainer=Color(0xFF410002),
                background=Color(0xFFFFFBFF),
                onBackground=Color(0xFF201A19),
                surface=Color(0xFFFFFBFF),
                onSurface=Color(0xFF201A19),
                surfaceVariant=Color(0xFFF5DED8),
                onSurfaceVariant=Color(0xFF53433F),
                outline=Color(0xFF85736E),
                inverseOnSurface=Color(0xFFFBEEEB),
                inverseSurface=Color(0xFF362F2D),
                inversePrimary=Color(0xFFFFB5A0),
                surfaceTint=Color(0xFF000000),
                outlineVariant=Color(0xFFB12D00),
                scrim=Color(0xFFD8C2BC)
            )
            override val typography: Typography = Typography()
            override val shapes: Shapes = Shapes()
        },
        OrangeDark {
            override val colors: ColorScheme = ColorScheme(
                primary=Color(0xFFFFB5A0),
                onPrimary=Color(0xFF601400),
                primaryContainer=Color(0xFF872100),
                onPrimaryContainer=Color(0xFFFFDBD1),
                secondary=Color(0xFFE7BDB2),
                onSecondary=Color(0xFF442A22),
                secondaryContainer=Color(0xFF5D4038),
                onSecondaryContainer=Color(0xFFFFDBD1),
                tertiary=Color(0xFFD9C58D),
                onTertiary=Color(0xFF3B2F05),
                tertiaryContainer=Color(0xFF534619),
                onTertiaryContainer=Color(0xFFF6E1A6),
                error=Color(0xFFFFB4AB),
                errorContainer=Color(0xFF93000A),
                onError=Color(0xFF690005),
                onErrorContainer=Color(0xFFFFDAD6),
                background=Color(0xFF201A19),
                onBackground=Color(0xFFEDE0DD),
                surface=Color(0xFF201A19),
                onSurface=Color(0xFFEDE0DD),
                surfaceVariant=Color(0xFF53433F),
                onSurfaceVariant=Color(0xFFD8C2BC),
                outline=Color(0xFFA08C87),
                inverseOnSurface=Color(0xFF201A19),
                inverseSurface=Color(0xFFEDE0DD),
                inversePrimary=Color(0xFFB12D00),
                surfaceTint=Color(0xFF000000),
                outlineVariant=Color(0xFFFFB5A0),
                scrim= Color(0xFF53433F)
            )
            override val typography: Typography = Typography()
            override val shapes: Shapes = Shapes()
        },

        GreenLight {
            override val colors: ColorScheme =ColorScheme(
                primary=Color(0xFF256D00),
                onPrimary=Color(0xFFFFFFFF),
                primaryContainer=Color(0xFF8AFD59),
                onPrimaryContainer=Color(0xFF062100),
                secondary=Color(0xFF55624C),
                onSecondary=Color(0xFFFFFFFF),
                secondaryContainer=Color(0xFFD8E7CB),
                onSecondaryContainer=Color(0xFF131F0D),
                tertiary=Color(0xFF386667),
                onTertiary=Color(0xFFFFFFFF),
                tertiaryContainer=Color(0xFFBBEBEC),
                onTertiaryContainer=Color(0xFF002021),
                error=Color(0xFFBA1A1A),
                errorContainer=Color(0xFFFFDAD6),
                onError=Color(0xFFFFFFFF),
                onErrorContainer=Color(0xFF410002),
                background=Color(0xFFFDFDF6),
                onBackground=Color(0xFF1A1C18),
                surface=Color(0xFFFDFDF6),
                onSurface=Color(0xFF1A1C18),
                surfaceVariant=Color(0xFFDFE4D7),
                onSurfaceVariant=Color(0xFF43483E),
                outline=Color(0xFF73796D),
                inverseOnSurface=Color(0xFFF1F1EA),
                inverseSurface=Color(0xFF2F312D),
                inversePrimary=Color(0xFF6FDF3E),
                surfaceTint=Color(0xFF000000),
                outlineVariant=Color(0xFF256D00),
                scrim=Color(0xFFC3C8BB),
            )
            override val typography: Typography = Typography()
            override val shapes: Shapes = Shapes()
        },
        GreenDark {
            override val colors: ColorScheme = ColorScheme(
                primary=Color(0xFF6FDF3E),
                onPrimary=Color(0xFF0F3900),
                primaryContainer=Color(0xFF1A5200),
                onPrimaryContainer=Color(0xFF8AFD59),
                secondary=Color(0xFFBCCBB0),
                onSecondary=Color(0xFF283421),
                secondaryContainer=Color(0xFF3E4A36),
                onSecondaryContainer=Color(0xFFD8E7CB),
                tertiary=Color(0xFFA0CFD0),
                onTertiary=Color(0xFF003738),
                tertiaryContainer=Color(0xFF1E4E4F),
                onTertiaryContainer=Color(0xFFBBEBEC),
                error=Color(0xFFFFB4AB),
                errorContainer=Color(0xFF93000A),
                onError=Color(0xFF690005),
                onErrorContainer=Color(0xFFFFDAD6),
                background=Color(0xFF1A1C18),
                onBackground=Color(0xFFE3E3DC),
                surface=Color(0xFF1A1C18),
                onSurface=Color(0xFFE3E3DC),
                surfaceVariant=Color(0xFF43483E),
                onSurfaceVariant=Color(0xFFC3C8BB),
                outline=Color(0xFF8D9287),
                inverseOnSurface=Color(0xFF1A1C18),
                inverseSurface=Color(0xFFE3E3DC),
                inversePrimary=Color(0xFF256D00),
                surfaceTint=Color(0xFF000000),
                outlineVariant=Color(0xFF6FDF3E),
                scrim=Color(0xFF43483E),
            )
            override val typography: Typography = Typography()
            override val shapes: Shapes = Shapes()
        },

        BlueLight {
            override val colors: ColorScheme =  ColorScheme(
                primary=Color(0xFFA5C8FF),
                onPrimary=Color(0xFF00315F),
                primaryContainer=Color(0xFF004786),
                onPrimaryContainer=Color(0xFFD4E3FF),
                secondary=Color(0xFFBCC7DC),
                onSecondary=Color(0xFF273141),
                secondaryContainer=Color(0xFF3D4758),
                onSecondaryContainer=Color(0xFFD8E3F8),
                tertiary=Color(0xFFDABDE2),
                onTertiary=Color(0xFF3D2946),
                tertiaryContainer=Color(0xFF553F5D),
                onTertiaryContainer=Color(0xFFF7D8FF),
                error=Color(0xFFFFB4AB),
                errorContainer=Color(0xFF93000A),
                onError=Color(0xFF690005),
                onErrorContainer=Color(0xFFFFDAD6),
                background=Color(0xFF1A1C1E),
                onBackground=Color(0xFFE3E2E6),
                surface=Color(0xFF1A1C1E),
                onSurface=Color(0xFFE3E2E6),
                surfaceVariant=Color(0xFF43474E),
                onSurfaceVariant=Color(0xFFC3C6CF),
                outline=Color(0xFF8D9199),
                inverseOnSurface=Color(0xFF1A1C1E),
                inverseSurface=Color(0xFFE3E2E6),
                inversePrimary=Color(0xFF005FAF),
                surfaceTint=Color(0xFF000000),
                outlineVariant=Color(0xFFA5C8FF),
                scrim=Color(0xFF43474E),
            )
            override val typography: Typography = Typography()
            override val shapes: Shapes = Shapes()

        },
        BlueDark {
            override val colors: ColorScheme = ColorScheme(
                primary=Color(0xFF005FAF),
                onPrimary=Color(0xFFFFFFFF),
                primaryContainer=Color(0xFFD4E3FF),
                onPrimaryContainer=Color(0xFF001C3A),
                secondary=Color(0xFF545F71),
                onSecondary=Color(0xFFFFFFFF),
                secondaryContainer=Color(0xFFD8E3F8),
                onSecondaryContainer=Color(0xFF111C2B),
                tertiary=Color(0xFF6E5676),
                onTertiary=Color(0xFFFFFFFF),
                tertiaryContainer=Color(0xFFF7D8FF),
                onTertiaryContainer=Color(0xFF271430),
                error=Color(0xFFBA1A1A),
                errorContainer=Color(0xFFFFDAD6),
                onError=Color(0xFFFFFFFF),
                onErrorContainer=Color(0xFF410002),
                background=Color(0xFFFDFCFF),
                onBackground=Color(0xFF1A1C1E),
                surface=Color(0xFFFDFCFF),
                onSurface=Color(0xFF1A1C1E),
                surfaceVariant=Color(0xFFE0E2EC),
                onSurfaceVariant=Color(0xFF43474E),
                outline=Color(0xFF74777F),
                inverseOnSurface=Color(0xFFF1F0F4),
                inverseSurface=Color(0xFF2F3033),
                inversePrimary=Color(0xFFA5C8FF),
                surfaceTint=Color(0xFF000000),
                outlineVariant=Color(0xFF005FAF),
                scrim=Color(0xFFC3C6CF),
            )
            override val typography: Typography = Typography()
            override val shapes: Shapes = Shapes()
        }
        ;
        abstract val colors: ColorScheme
        abstract val typography: Typography
        abstract val shapes: Shapes
    }

    @Composable
    fun BasedOnSettings(content: @Composable () -> Unit){
        var currentTheme : Values by remember { mutableStateOf(Values.OrangeDark) }

        LaunchedEffect(Firebase.auth.currentUser){
            if(Firebase.auth.currentUser == null) return@LaunchedEffect

            Firebase.currentTheme { currentTheme = it }
        }

        MaterialTheme(
            colorScheme = currentTheme.colors,
            typography = currentTheme.typography,
            shapes = currentTheme.shapes,
            content = content
        )
    }
}