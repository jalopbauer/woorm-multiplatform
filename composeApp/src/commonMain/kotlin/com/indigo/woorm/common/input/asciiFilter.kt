package com.indigo.woorm.common.input

fun filterAscii(text: String, allowNewlineAndTab: Boolean = false): String {
    val allowed = if (allowNewlineAndTab) {
        text.filter { it == '\n' || it == '\t' || it.code in 0x20..0x7E }
    } else {
        text.filter { it.code in 0x20..0x7E }
    }
    return allowed
}
