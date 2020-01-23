package yoktavian.com.mvsp.route

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import yoktavian.com.mvsp.R

/**
 * Created by YudaOktavian on 04/02/2019
 */
object Router {
    const val FRAGMENT_TAG = "fragmentTag"
    const val BACK_BUTTON_TAG = "backButtonTag"

    fun go(oldFrag: Fragment, newFrag: Fragment) {
        oldFrag.fragmentManager?.beginTransaction()?.add(R.id.frameContainer, newFrag)?.commit()
    }

    fun goToNewActivity(
        oldAct: Activity,
        requestCode: Int? = null,
        intent: Intent,
        isOldActivityNeedToFinish: Boolean = false
    ) {
        oldAct.run {
            if (requestCode != null) startActivityForResult(intent, requestCode)
            else startActivity(intent)
        }
        if (isOldActivityNeedToFinish) oldAct.finish()
    }
}