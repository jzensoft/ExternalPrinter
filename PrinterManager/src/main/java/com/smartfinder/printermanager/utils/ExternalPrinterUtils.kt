package com.smartfinder.printermanager.utils

import android.graphics.Bitmap
import android.graphics.Color
import kotlin.math.roundToInt

class ExternalPrinterUtils {
    companion object {
        fun resizeBitmap(bitmap: Bitmap, scale: Int): Bitmap {
            return Bitmap.createScaledBitmap(
                bitmap,
                scale,
                (bitmap.height.toFloat() * scale / bitmap.width).roundToInt(),
                true
            )
        }

        fun convertBitmapToGrayscale(bitmap: Bitmap): Bitmap {
            val bwBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.RGB_565)
            val hsv = FloatArray(3)
            for (col in 0 until bitmap.width) {
                for (row in 0 until bitmap.height) {
                    Color.colorToHSV(bitmap.getPixel(col, row), hsv)
                    if (hsv[2] > 0.5f) {
                        bwBitmap.setPixel(col, row, -0x1)
                    } else {
                        bwBitmap.setPixel(col, row, -0x1000000)
                    }
                }
            }
            return bwBitmap
        }
    }
}