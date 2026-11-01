package com.indigo.woorm.util.inline

inline fun <T> T.runIf(condition: Boolean, block: T.() -> T): T =
    this.run {
        if(condition) {
            block()
        } else this
    }