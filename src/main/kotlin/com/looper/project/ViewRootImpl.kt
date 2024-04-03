package com.looper.project

class ViewRootImpl(
    private val choreographer: Choreographer,
) {

    private val inputRunnable = Runnable { doProcessInputEvents() }

    private val traversalRunnable = Runnable { doTraversal() }

    /**
     * Планируем обработку входящий нажатий.
     */
    fun scheduleConsumeInput() {
        choreographer.postCallback(Choreographer.CALLBACK_INPUT, inputRunnable)
    }

    /**
     * Планируем проход вью на следующий vsync
     */
    fun scheduleTraversals() {
        choreographer.postCallback(Choreographer.CALLBACK_TRAVERSAL, traversalRunnable)
    }

    /**
     * Проходимся по всему дереву вьюх и в зависимости от их флагов либо переизмеряем и перерисовываем,
     * либо просто перерисовываем.
     */
    private fun doTraversal() { }

    /**
     * Обрабатываем входящие нажатия.
     */
    private fun doProcessInputEvents() { }
}