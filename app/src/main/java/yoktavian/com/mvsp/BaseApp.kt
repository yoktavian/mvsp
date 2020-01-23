package yoktavian.com.mvsp

import android.app.Application
import android.content.Context

/**
 * Created by YudaOktavian on 23-Jan-2020
 */
class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this.applicationContext
    }

    companion object {
        private lateinit var appContext: Context
        fun getContext() = appContext
    }
}