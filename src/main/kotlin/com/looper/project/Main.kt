package com.looper.project

fun main() {
    ApplicationTime.init()

    Looper.prepareMainLooper()

    val looper = Looper.getMainLooper()

    MThread().start()

    looper.loop()
}

class MThread : Thread() {

    private val handler = Handler(Looper.getMainLooper())

    override fun run() {
        println("Первое сообщение")
        handler.post(
            runnable = { println("Сообщение 1 с 0 задержкой") },
        )
        handler.postDelayed(
            runnable = { println("Сообщение с 5000 задержкой") },
            delayMillis = 5000,
        )
        handler.postDelayed(
            runnable = { println("Сообщение с 10000 задержкой") },
            delayMillis = 10000,
        )
        handler.post(
            runnable = { println("Сообщение 2 с 0 задержкой") },
        )
        handler.postDelayed(
            runnable = { println("Сообщение с 2000 задержкой") },
            delayMillis = 2000,
        )
    }
}







