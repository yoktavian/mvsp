package yoktavian.com.mvsp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import yoktavian.com.mvsp.screen.user.UserDetailScreen

class BaseActivity : AppCompatActivity() {

    lateinit var mainFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainFragment = UserDetailScreen.newInstance()
        // Start Fragment.
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frameContainer, mainFragment)
            .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mainFragment.onActivityResult(requestCode, resultCode, data)
    }
}
