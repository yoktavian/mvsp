package yoktavian.com.mvsp.component

import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.simple_list_layout.view.*
import yoktavian.com.mvsp.R
import yoktavian.com.mvsp.base.ui.BaseModule
import yoktavian.com.mvsp.util.asViewGroup

class SimpleModule(
    private val view: View
) : BaseModule<SimpleModule.State, SimpleModule>(view) {

    class State {
        var sampleText: String = ""
    }

    override val internalState: State = State()
    override var identifier: String = ""

    override fun render() {
        view.sampleText.text = internalState.sampleText
    }

    override fun bindState(state: State.() -> Unit): SimpleModule {
        state(internalState)
        return this
    }

    override fun setIdentifier(identifier: String): SimpleModule {
        this.identifier = identifier
        return this
    }

    companion object {
        fun createView(view: View): View {
            return LayoutInflater.from(view.context)
                .inflate(R.layout.simple_list_layout, view.asViewGroup(), false)
        }
    }
}