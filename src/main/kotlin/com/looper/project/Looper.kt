package com.looper.project

class Looper private constructor() {

    companion object {

        private val threadLocal = ThreadLocal<Looper>()

        private var mainLooper: Looper? = null

        fun prepare() {
            if (threadLocal.get() != null) {
                throw RuntimeException("Only one Looper may be created per thread")
            }
            threadLocal.set(Looper())
        }

        fun prepareMainLooper() {
            prepare()
            if (mainLooper != null) {
                throw IllegalStateException("The main Looper has already been prepared.")
            }
            mainLooper = myLooper()
        }

        fun myLooper() = threadLocal.get()

        fun getMainLooper(): Looper = mainLooper
            ?: throw IllegalStateException("Before this")
    }

    val messageQueue = MessageQueue()

    private var isRunning: Boolean = true

    fun loop() {
        while (isRunning) {
            val nextMessage: Message = messageQueue.next()
            nextMessage.block.run()
        }
    }
}