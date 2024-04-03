package com.looper.project

import java.util.*

class Choreographer {

    companion object {

        const val CALLBACK_INPUT: Int = 0

        const val CALLBACK_ANIMATION: Int = 1

        const val CALLBACK_TRAVERSAL: Int = 2

        const val CALLBACK_COMMIT: Int = 3
    }

    private val handler = Handler(Looper.myLooper())

    private val callbacks: MutableList<LinkedList<Runnable>> = ArrayList<LinkedList<Runnable>>(4).apply {
        add(CALLBACK_INPUT, LinkedList())
        add(CALLBACK_ANIMATION, LinkedList())
        add(CALLBACK_TRAVERSAL, LinkedList())
        add(CALLBACK_COMMIT, LinkedList())
    }

    private val frameDisplayEventReceiver = object : DisplayEventReceiver() {

        override fun onVsync() {

            handler.post(
                runnable = { doFrame() },
            )
        }
    }

    fun postCallback(callbackType: Int, callback: Runnable, delayMillis: Long = 0) {
        val callbacksByType: LinkedList<Runnable> = callbacks.getOrNull(callbackType) ?: return
        callbacksByType.add(callback)

        val now: Long = ApplicationTime.uptimeMillis
        val dueTime: Long = now + delayMillis

        if (dueTime <= now) {
            doScheduleVsync()
        } else {
            handler.postDelayed(
                runnable = { doScheduleVsync() },
                delayMillis = dueTime,
            )
        }
    }

    private fun doFrame() {

        doCallbacks(CALLBACK_INPUT)
        doCallbacks(CALLBACK_ANIMATION)
        doCallbacks(CALLBACK_TRAVERSAL)
        doCallbacks(CALLBACK_COMMIT)
    }

    /**
     * Запрашиваем Vsync у системы.
     */
    private fun doScheduleVsync() {
        frameDisplayEventReceiver.scheduleVsync()
    }

    private fun doCallbacks(callbackType: Int) {
        val callbacksOfType: LinkedList<Runnable> = callbacks
            .getOrNull(callbackType)
            .takeIf { it?.isNotEmpty() == true }
            ?: return

        runCatching { callbacksOfType.forEach { it.run() } }

        callbacks[callbackType] = LinkedList()
    }
}

abstract class DisplayEventReceiver {

    abstract fun onVsync()

    /**
     * Планирует подачу одного импульса вертикальной синхронизации при начале следующего кадра.
     *
     * Его отравит система, дернув [onVsync].
     */
    fun scheduleVsync() { /* call nativeScheduleVsync() */ }
}