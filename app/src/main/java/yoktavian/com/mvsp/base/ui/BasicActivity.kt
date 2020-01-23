package yoktavian.com.mvsp.base.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        (supportFragmentManager.fragments[0] as? BaseFragment<Any, Any>)?.onActivityResult(
            requestCode, resultCode, data
        )
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
    }
}