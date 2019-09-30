package yoktavian.com.mvsp.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import yoktavian.com.mvsp.R
import yoktavian.com.mvsp.route.PasserFragment
import yoktavian.com.mvsp.route.Router

class BasicActivity : AppCompatActivity() {

    private var isBackButtonEnable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)
        val resultFragment = intent.getStringExtra(Router.FRAGMENT_TAG)
        val resultBackButton = intent.getBooleanExtra(Router.BACK_BUTTON_TAG, false)
        if (resultFragment != null) {
            val fragment = PasserFragment.getFragment(resultFragment)
            fragment?.let {
                set(resultBackButton, it)
            }
        }
    }

    private fun set(isBackButtonEnable: Boolean, fragment: Fragment) {
        this.isBackButtonEnable = isBackButtonEnable
        setupToolbar()
        attachFragment(fragment)
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true.takeIf { isBackButtonEnable } ?: false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun attachFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).commit()
        fragmentManager.executePendingTransactions()
    }
}
