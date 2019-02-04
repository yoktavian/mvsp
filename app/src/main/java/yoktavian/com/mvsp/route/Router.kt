package yoktavian.com.mvsp.route

import android.support.v4.app.Fragment
import yoktavian.com.mvsp.R

/**
 * Created by YudaOktavian on 04/02/2019
 */
object Router {
    fun go(oldFrag: Fragment, newFrag: Fragment) {
        oldFrag.fragmentManager?.beginTransaction()?.add(R.id.frameContainer, newFrag)?.commit()
    }
}