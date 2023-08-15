package com.smartfinder.printermanager.utils

enum class EpsonModel(val value: Int) {
    MODEL_ANK(0),
    MODEL_JAPANESE(1),
    MODEL_CHINESE(2),
    MODEL_TAIWAN(3),
    MODEL_KOREAN(4),
    MODEL_THAI(5),
    MODEL_SOUTHASIA(6);

    companion object {
        fun getModel(m: String): Int {
            return when ("MODEL_${m.uppercase()}") {
                MODEL_ANK.name -> MODEL_ANK.value
                MODEL_JAPANESE.name -> MODEL_JAPANESE.value
                MODEL_TAIWAN.name -> MODEL_TAIWAN.value
                MODEL_KOREAN.name -> MODEL_KOREAN.value
                MODEL_THAI.name -> MODEL_THAI.value
                MODEL_SOUTHASIA.name -> MODEL_SOUTHASIA.value
                else -> MODEL_CHINESE.value
            }
        }
    }
}