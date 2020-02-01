package yoktavian.com.mvsp.component

import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.simple_list_w_img_layout.view.*
import yoktavian.com.mvsp.R
import yoktavian.com.mvsp.base.ui.BaseModule
import yoktavian.com.mvsp.util.asViewGroup
import yoktavian.com.mvsp.util.csText

class SimpleWithImgModule(
    private val view: View
) : BaseModule<SimpleWithImgModule.State, SimpleWithImgModule>(view) {

    class State {
        var sampleText: String = ""
        var image: Int = 0
        var onClickListener: ((View) -> Unit)? = null
    }

    override val internalState: State = State()
    override var identifier: String = ""

    override fun render() {
        view.apply {
            sampleText.csText = internalState.sampleText
            launcher.setImageResource(internalState.image)
            setOnClickListener(internalState.onClickListener)
        }
    }

    override fun bindState(state: State.() -> Unit): SimpleWithImgModule {
        state(internalState)
        return this
    }

    override fun setIdentifier(identifier: String): SimpleWithImgModule {
        this.identifier = identifier
        return this
    }

    companion object {
        fun createView(view: View): View {
            return LayoutInflater.from(view.context)
                .inflate(R.layout.simple_list_w_img_layout, view.asViewGroup(), false)
        }
    }
}