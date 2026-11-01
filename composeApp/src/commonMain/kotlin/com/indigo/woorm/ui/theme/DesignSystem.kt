package com.indigo.woorm.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class SemanticDomain(
    val surface: Color,
    val background: Color,
    val stroke: Color,
    val primary: Color,
    val text: Color,
    val highlight: Color
)

@Immutable
data class AvatarSet(
    val user: Color,
    val club: Color,
    val text: Color
)

@Immutable
data class Avatars(
    val blue: AvatarSet,
    val green: AvatarSet,
    val yellow: AvatarSet,
    val red: AvatarSet
)

@Immutable
data class DomainColors(
    val neutral: SemanticDomain,
    val primary: SemanticDomain,
    val error: SemanticDomain,
    val success: SemanticDomain,
    val avatars: Avatars,
    val star: Color
)

val LightDomainColors = DomainColors(
    neutral = SemanticDomain(
        surface = Brown50,
        background = Brown100,
        stroke = Brown200,
        primary = Brown500,
        highlight = Brown700,
        text = Brown800
    ),
    primary = SemanticDomain(
        surface = Red500, // TODO change
        background = Blue50,
        stroke = Blue200,
        primary = Blue500,
        text = Blue800,
        highlight = Red500 // TODO change
    ),
    error = SemanticDomain(
        surface = Blue500,  // TODO change
        background = Red100,
        stroke = Red200,
        primary = Red500,
        text = Red800,
        highlight = Blue500 // TODO change
    ),
    avatars = Avatars(
        blue = AvatarSet(
            user = Blue100,
            club = Blue200,
            text = Blue800
        ),
        green = AvatarSet(
            user = Green100,
            club = Green200,
            text = Green800
        ),
        yellow = AvatarSet(
            user = Yellow100,
            club = Yellow200,
            text = Yellow600
        ),
        red = AvatarSet(
            user = Red100,
            club = Red200,
            text = Red600
        )
    ),
    success = SemanticDomain(
        surface = Blue500,  // TODO change
        background = Green100,
        stroke = Green200,
        primary = Green500,
        text = Green800,
        highlight = Blue500 // TODO change
    ),
    star = Yellow400
)
val DarkDomainColors = LightDomainColors

val LocalDomainColors = staticCompositionLocalOf { LightDomainColors }