package com.daisy.picky.login

import android.app.Application
import com.daisy.picky.BuildConfig
import com.kakao.sdk.common.KakaoSdk

class  KakaoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
    }
}