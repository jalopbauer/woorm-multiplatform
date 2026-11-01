package com.indigo.woorm.common.icon

import org.jetbrains.compose.resources.DrawableResource
import woorm.composeapp.generated.resources.*

sealed interface AppIcon {
    val res: DrawableResource
    data object ArrowLeft : AppIcon { override val res = Res.drawable.arrow_left }
    data object Shelf : AppIcon { override val res = Res.drawable.shelf }
    data object BookCheck : AppIcon { override val res = Res.drawable.book_check }
    data object BookOpen : AppIcon { override val res = Res.drawable.book_open }
    data object BookX : AppIcon { override val res = Res.drawable.book_x }
    data object CalendarCheck : AppIcon { override val res = Res.drawable.calendar_check }
    data object CalendarPlus2 : AppIcon { override val res = Res.drawable.calendar_plus_2 }
    data object Check : AppIcon { override val res = Res.drawable.check }
    data object ChevronDown : AppIcon { override val res = Res.drawable.chevron_down }
    data object Goal : AppIcon { override val res = Res.drawable.goal }
    data object Heart : AppIcon { override val res = Res.drawable.heart }
    data object HeartFilled : AppIcon { override val res = Res.drawable.heart_filled }
    data object House : AppIcon { override val res = Res.drawable.house }
    data object LibraryBig : AppIcon { override val res = Res.drawable.library_big }
    data object Pencil : AppIcon { override val res = Res.drawable.pencil }
    data object Plus : AppIcon { override val res = Res.drawable.plus }
    data object RotateCcw : AppIcon { override val res = Res.drawable.rotate_ccw }
    data object Search : AppIcon { override val res = Res.drawable.search }
    data object Share2 : AppIcon { override val res = Res.drawable.share_2 }
    data object Square : AppIcon { override val res = Res.drawable.square }
    data object StarLeftEmpty : AppIcon { override val res = Res.drawable.star_left_empty }
    data object StarLeftFilled : AppIcon { override val res = Res.drawable.star_left_filled }
    data object StarFilled : AppIcon { override val res = Res.drawable.star_selected_true }
    data object StarRightEmpty : AppIcon { override val res = Res.drawable.star_right_empty }
    data object StarRightFilled : AppIcon { override val res = Res.drawable.star_right_filled }
    data object Target : AppIcon { override val res = Res.drawable.target }
    data object Trash2 : AppIcon { override val res = Res.drawable.trash_2 }
    data object UsersRound : AppIcon { override val res = Res.drawable.users_round }
    data object Worm : AppIcon { override val res = Res.drawable.worm }
    data object X : AppIcon { override val res = Res.drawable.x }
}
