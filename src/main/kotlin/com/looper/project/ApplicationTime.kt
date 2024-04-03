package com.looper.project

object ApplicationTime {

    val uptimeMillis: Long
        get() = System.currentTimeMillis() - startTimeMillis

    var startTimeMillis: Long = 0
        private set

    fun init() {
        startTimeMillis = System.currentTimeMillis()
    }
}