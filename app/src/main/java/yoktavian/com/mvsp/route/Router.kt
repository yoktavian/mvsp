package yoktavian.com.mvsp.route

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import yoktavian.com.mvsp.R
import yoktavian.com.mvsp.base.BasicActivity

/**
 * Created by YudaOktavian on 04/02/2019
 */
object Router {
    const val FRAGMENT_TAG = "fragmentTag"
    const val BACK_BUTTON_TAG = "backButtonTag"

    fun go(oldFrag: Fragment, newFrag: Fragment) {
        oldFrag.fragmentManager?.beginTransaction()?.add(R.id.frameContainer, newFrag)?.commit()
    }

    fun goToNewActivityWithFragment(
        context: Activity,
        isEnableBackButton: Boolean,
        fragment: Fragment,
        requestCode: Int? = null
    ) {
        val fragmentTag = PasserFragment.setFragment(fragment)
        val intent = Intent(context, BasicActivity::class.java).apply {
            putExtra(FRAGMENT_TAG, fragmentTag)
            putExtra(BACK_BUTTON_TAG, isEnableBackButton)
        }
        context.run {
            if (requestCode != null) startActivityForResult(intent, requestCode)
            else startActivity(intent)
        }
    }
}