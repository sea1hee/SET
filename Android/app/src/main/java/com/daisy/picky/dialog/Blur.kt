package com.daisy.picky.dialog

import android.os.Build
import android.util.Log
import android.view.Window
import android.view.WindowManager

class Blur {
    companion object {
        fun setDialogBlur(window: Window) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Log.d("blurLog", window.windowManager.isCrossWindowBlurEnabled.toString())
                if (window.windowManager.isCrossWindowBlurEnabled) {
                    window.setFlags(
                        WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                        WindowManager.LayoutParams.FLAG_BLUR_BEHIND
                    )
                    window.attributes.blurBehindRadius = 0
                    window.setBackgroundBlurRadius(50)
                }
            }
        }
    }

}