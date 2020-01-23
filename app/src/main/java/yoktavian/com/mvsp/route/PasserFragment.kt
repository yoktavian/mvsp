package yoktavian.com.mvsp.route

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import yoktavian.com.mvsp.BaseActivity
import yoktavian.com.mvsp.route.Router.BACK_BUTTON_TAG
import yoktavian.com.mvsp.route.Router.FRAGMENT_TAG

/**
 * Created by YudaOktavian on 30-Sep-2019
 *
 * It's created because at this architecture, we try to design and
 * implement one activity can be attached by dynamic fragment.
 * Of course we can't passing fragment via intent, so fragment
 * will saved at the hasmap with tagId. You can passing the tag of
 * fragment, then if you want to attach fragment at BasicActivity
 * you can get the tagID via intent, then get the fragment using tagID.
 * After success to get the tagID of fragment, then fragment with tagID
 * will removed from this object, to avoid memory leak.
 */
object PasserFragment {
    private val fragments = HashMap<String, Fragment>()

    fun setFragment(fragment: Fragment): String {
        val tag = fragment.javaClass.simpleName
        fragments[tag] = fragment
        return tag
    }

    fun goToNewActivityWithFragment(
        context: Activity,
        isEnableBackButton: Boolean,
        fragment: Fragment,
        requestCode: Int? = null,
        isNeedToClearTask: Boolean = false
    ) {
        val activity = BaseActivity::class.java

        val fragmentTag = PasserFragment.setFragment(fragment)
        val intent = Intent(context, activity).apply {
            putExtra(FRAGMENT_TAG, fragmentTag)
            putExtra(BACK_BUTTON_TAG, isEnableBackButton)
            // add new flag to clear task when needed
            if (isNeedToClearTask) flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.run {
            if (requestCode != null) startActivityForResult(intent, requestCode)
            else startActivity(intent)
        }
    }

    fun getFragment(tag: String): Fragment? {
        var fragment: Fragment? = null
        if (fragments.containsKey(tag)) {
            fragment = fragments[tag]
            fragments.remove(tag)
        }
        return fragment
    }
}