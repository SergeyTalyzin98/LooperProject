package com.looper.project

class Message(
    val block: Runnable,
    val whenStart: Long = 0,
    var next: Message? = null,
)