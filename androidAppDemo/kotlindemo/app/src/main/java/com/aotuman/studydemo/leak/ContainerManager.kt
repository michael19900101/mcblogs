package com.aotuman.studydemo.leak

class ContainerManager {

    companion object {

        var dummyListenerList : MutableList<DummyListener> = mutableListOf()

        interface DummyListener {
            fun onAction()
        }

        fun registerListener(listener : DummyListener) {
            dummyListenerList.add(listener)
        }

        fun unregisterListener(listener : DummyListener) {
            dummyListenerList.remove(listener)
        }
    }


}