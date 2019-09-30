package yoktavian.com.mvsp.base

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import kotlinx.android.synthetic.main.activity_basic.*
import yoktavian.com.mvsp.R
import yoktavian.com.mvsp.route.PasserFragment
import yoktavian.com.mvsp.route.Router

class BasicActivity : AppCompatActivity() {

    private var isBackButtonEnable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)
        val resultIntent = intent.getStringExtra(Router.FRAGMENT_TAG)
        if (resultIntent != null) {
            val fragment = PasserFragment.getFragment(resultIntent)
            fragment?.let { set(true, it) }
            Log.d("=>Res", "not null")
        }
    }

    private fun set(isBackButtonEnable: Boolean, fragment: Fragment) {
        this.isBackButtonEnable = isBackButtonEnable
        setupToolbar()
        attachFragment(fragment)
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true.takeIf { isBackButtonEnable } ?: false)
        Log.d("=>Res", "set toolbar")
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun attachFragment(fragment: Fragment) {
        Log.d("=>Res", "attach fragment")
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).commit()
        fragmentManager.executePendingTransactions()
    }
}
