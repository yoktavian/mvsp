package yoktavian.com.mvsp.base.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import yoktavian.com.mvsp.util.compareWith

/**
 * Created by YudaOktavian on 24-Jan-2020
 */
class BaseAdapter(private var modules: List<BaseModule<*, *>> = emptyList()) :
    RecyclerView.Adapter<BaseModule<*, *>>() {

    override fun getItemCount() = modules.size

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(parent: ViewGroup, position: Int)
            : BaseModule<*, *> = modules[position]

    override fun onBindViewHolder(holder: BaseModule<*, *>, position: Int) {
        (holder as? BaseModule)?.render()
    }

    fun set(modules: List<BaseModule<*, *>>) {
        if (this.modules != modules) {
            this.modules = emptyList()
            this.modules = modules
        }
        val diff = this.modules.compareWith(modules)
        diff.dispatchUpdatesTo(this)
    }
}