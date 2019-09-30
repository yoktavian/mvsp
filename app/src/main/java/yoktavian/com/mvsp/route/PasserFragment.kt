package yoktavian.com.mvsp.route

import android.support.v4.app.Fragment
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by YudaOktavian on 30-Sep-2019
 *
 * It's created because at this architecture, we try to design and
 * implement one activity can be attached by dynamic fragment.
 * You know that we can passing fragment via intent, so fragment
 * will saved at the hasmap with tagId. You can passing the tag of
 * fragment, then if you want to attach fragment at BasicActivity
 * you can get the tagID via intent, then get the fragment using tagID.
 * After success to get the tagID of fragment, then fragment with tagID
 * will removed from this object, to avoid memory leak.
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
        fragments[tag] = fragment
        return tag
    }
}