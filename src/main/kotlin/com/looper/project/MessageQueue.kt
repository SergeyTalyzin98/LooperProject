package com.looper.project

class MessageQueue {

    private var messages: Message? = null

    private val monitor = Object()

    fun next(): Message = synchronized(monitor) {
        val currentMessage: Message = messages ?: run {
            monitor.wait()
            return@run next()
        }

        val timeout: Long = currentMessage.whenStart - ApplicationTime.uptimeMillis
        if (timeout > 0) {
            monitor.wait(timeout)
            return next()
        }

        return currentMessage.also { messages = currentMessage.next }
    }

    fun add(newMessage: Message) = synchronized(monitor) {
        var current: Message = messages ?: run {
            messages = newMessage
            monitor.notifyAll()
            return
        }
        var previous: Message? = null
        while (true) {
            if (current.whenStart > newMessage.whenStart) {
                newMessage.next = current
                if (previous != null) {
                    previous.next = newMessage
                } else {
                    messages = newMessage
                }
                break
            }
            current = current.next ?: run {
                current.next = newMessage
                monitor.notifyAll()
                return
            }
            previous = current
        }
        monitor.notifyAll()
    }
}