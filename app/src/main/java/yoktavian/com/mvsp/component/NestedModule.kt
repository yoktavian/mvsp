package yoktavian.com.mvsp.component

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.nested_list_layout.view.*
import kotlinx.android.synthetic.main.user_detail_layout.view.*
import yoktavian.com.mvsp.R
import yoktavian.com.mvsp.base.ui.BaseAdapter
import yoktavian.com.mvsp.base.ui.BaseModule
import yoktavian.com.mvsp.util.asViewGroup

/**
 * Created by YudaOktavian on 31-Jan-2020
 */
class NestedModule(private val view: View) : BaseModule<NestedModule.State, NestedModule>(view) {
    class State {
        var viewPool: RecyclerView.RecycledViewPool? = null
        var modules: List<BaseModule<*, *>> = emptyList()
    }

    override val internalState: State = State()
    override var identifier: String = ""
    private var adapter: BaseAdapter = BaseAdapter(internalState.modules)

    init {
        view.nestedRecyclerView.adapter = adapter
    }

    override fun render() {
        view.apply {
            adapter.set(internalState.modules)
            nestedRecyclerView.setRecycledViewPool(internalState.viewPool)
        }
    }

    override fun bindState(state: State.() -> Unit): NestedModule {
        state(internalState)
        return this
    }

    override fun setIdentifier(identifier: String): NestedModule {
        this.identifier = identifier
        return this
    }

    companion object {
        fun createView(view: View): View {
            return LayoutInflater.from(view.context)
                .inflate(R.layout.nested_list_layout, view.asViewGroup(), false)
        }
    }
}