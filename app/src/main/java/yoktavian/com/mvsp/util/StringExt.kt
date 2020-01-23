package yoktavian.com.mvsp.util

import yoktavian.com.mvsp.BaseApp

/**
 * Created by YudaOktavian on 02-Oct-2019
 * hi testing ci.
 */
fun Int.getString() = BaseApp.getContext().resources.getString(this).toString()
fun Int.getStringArray() = BaseApp.getContext().resources.getStringArray(this)