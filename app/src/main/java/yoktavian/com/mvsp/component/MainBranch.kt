package yoktavian.com.mvsp.component

import android.view.ViewGroup

/**
 * Created by YudaOktavian on 30-Jul-2019
 */
abstract class MainBranch : SingleLeaf() {
    // branch mean viewGroup
    private val branch get() = getView() as ViewGroup

    fun addLeaf(leaf: SingleLeaf, index: Int = -1, params: ViewGroup.LayoutParams? = null) {
        branch.addLeaf(leaf, index, params)
    }

    private fun ViewGroup.addLeaf(leaf: SingleLeaf, index: Int = -1, params: ViewGroup.LayoutParams? = null) {
        val view = leaf.getView()
        val child = index.takeIf { index != -1 } ?: childCount
        params?.let { addView(view, child, it) } ?: addView(view, child)
    }
}