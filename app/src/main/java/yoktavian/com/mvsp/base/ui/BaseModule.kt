package yoktavian.com.mvsp.base.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseModule<S, M>(view: View) : RecyclerView.ViewHolder(view),
    ModuleContract<S> {
    abstract fun render()
    abstract fun bindState(state: S.() -> Unit): M
    abstract fun setIdentifier(identifier: String): M
}