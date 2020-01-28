package yoktavian.com.mvsp.base.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by YudaOktavian on 24-Jan-2020
 */
class BaseAdapter(private val modules: List<BaseModule<*, *>>)
    : RecyclerView.Adapter<BaseModule<*, *>>() {

    override fun getItemCount() = modules.size

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(parent: ViewGroup, position: Int)
            : BaseModule<*, *> = modules[position]

    override fun onBindViewHolder(holder: BaseModule<*, *>, position: Int) {
        (holder as? BaseModule)?.render()
    }
}

