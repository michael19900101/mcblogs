package com.aotuman.studydemo.leak

class HandlePhotoService {

    companion object {
        fun handlePhoto (photo :IHandlerPhoto) {
            Thread(Runnable {
                Thread.sleep(30000)
                photo.handlePhoto()
            }).start()
        }
    }
}