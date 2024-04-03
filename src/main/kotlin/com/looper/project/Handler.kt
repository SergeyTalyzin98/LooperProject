package com.looper.project

class Handler(
    looper: Looper,
) {

    private val queue: MessageQueue = looper.messageQueue

    fun post(runnable: Runnable) {
        queue.add(Message(block = runnable))
    }

    fun postDelayed(runnable: Runnable, delayMillis: Long) {
        queue.add(Message(block = runnable, whenStart = delayMillis + ApplicationTime.uptimeMillis))
    }
}