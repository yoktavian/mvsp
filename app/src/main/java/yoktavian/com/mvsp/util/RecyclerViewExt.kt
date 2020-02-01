package yoktavian.com.mvsp.util

import androidx.recyclerview.widget.DiffUtil
import yoktavian.com.mvsp.base.ui.BaseModule

/**
 * Created by YudaOktavian on 31-Jan-2020
 */
fun List<BaseModule<*, *>>.compareWith(newItems: List<BaseModule<*, *>>): DiffUtil.DiffResult {
    return DiffUtil.calculateDiff(
        object : DiffUtil.Callback() {
            override fun getNewListSize() = newItems.size

            override fun getOldListSize() = this@compareWith.size

            override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
                return this@compareWith[oldPos] == newItems[newPos]
            }

            override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
                return this@compareWith[oldPos].identifier == newItems[newPos].identifier
            }
        }
    )
}