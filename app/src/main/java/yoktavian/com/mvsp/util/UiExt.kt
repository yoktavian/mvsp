package yoktavian.com.mvsp.util

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import yoktavian.com.mvsp.base.ui.BaseAdapter
import yoktavian.com.mvsp.base.ui.BaseModule

/**
 * Created by YudaOktavian on 15-Jan-2020
 */
inline var TextView.csText
    get() = text
    set(value) {
        if (value != text) text = value
    }

inline var View.csVisibility
    get() = visibility
    set(value) {
        if (value != visibility) visibility = value
    }

fun View.asViewGroup() = this as ViewGroup