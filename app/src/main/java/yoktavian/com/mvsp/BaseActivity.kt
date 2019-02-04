package yoktavian.com.mvsp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import yoktavian.com.mvsp.screen.user.UserDetailScreen

class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Start Fragment.
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frameContainer, UserDetailScreen.newInstance())
            .commit()
    }
}
