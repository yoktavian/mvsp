package yoktavian.com.mvsp.helper

import android.view.View
import android.widget.TextView

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