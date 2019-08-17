package yoktavian.com.mvsp.component

import android.view.View
import android.view.ViewGroup

/**
 * Created by YudaOktavian on 30-Jul-2019
 */
abstract class SingleLeaf {

    abstract fun getView(): View

    fun setId(id: Int) {
        getView().id = id
    }

    fun setParams(
        newWidth: Int? = null,
        newHeight: Int? = null
    ) {
        if (getView().layoutParams != null) {
            getView().layoutParams.apply {
                width = newWidth ?: width
                height = newHeight ?: height
            }
        } else {
            getView().layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }
}