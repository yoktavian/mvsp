package yoktavian.com.mvsp.route

import android.support.v4.app.Fragment
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by YudaOktavian on 30-Sep-2019
 */
object PasserFragment {
    private val fragments = HashMap<String, Fragment>()
    fun getFragment(tag: String?): Fragment? {
        if (tag != null && fragments.containsKey(tag)) {
            val fragment = fragments[tag]
            fragments.remove(tag)
            return fragment
        }
        return null
    }

    fun setFragment(fragment: Fragment): String {
        val tag = UUID.randomUUID().toString()
        fragments.put(tag, fragment)
        return tag
    }
}