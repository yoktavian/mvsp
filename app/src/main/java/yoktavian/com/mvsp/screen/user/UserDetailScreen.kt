package yoktavian.com.mvsp.screen.user

import android.os.Bundle
import android.view.View
import yoktavian.com.mvsp.base.BaseFragment

/**
 * Created by YudaOktavian on 03/02/2019
 */
class UserDetailScreen : BaseFragment<UserDetailScreen.State, UserDetailScreen.Presenter, UserDetailScreen>(::UserDetailScreen) {
    class State : BaseFragment.State() {
        var isLoading = false
        var isNetworkError = false
    }

    class Presenter(val state: State, val view: UserDetailScreen) : BaseFragment.Presenter(state, view) {
        fun fetchUserDetail() {
            state.isLoading = true
            view.renderLoading()

            // get user detail from your API
            // region result
            state.isLoading = false
            view.renderLoading()
            // region on success

            // endregion

            // region on error

            // endregion
            // endregion
        }
    }

    override val state: State get() = State()

    override val presenter: Presenter get() = Presenter(state, this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity {
            if (state.isLoading) {
                // view of loading must be visible
            } else {
                // view of loading must be gone
            }
        }
    }

    /**
     * Every part of view have render method.
     * if any changes inside of State affected from Presenter,
     * of course it will determine view changes.
     * So you must calling partial render, based on
     * changes that occur in State.
     */
    override fun renderLoading() {
        super.renderLoading()
        /**
         * Call view to make sure this fragment
         * still alive to avoid memory leak.
         */
        view {
            if (state.isLoading) {
                // view of loading must be visible
            } else {
                // view of loading must be gone
            }
        }
    }

    override fun renderNetworkError() {
        super.renderNetworkError()
        view {
            if (state.isNetworkError) {
                // view of network error must be visible
            } else {
                // view of network error must be gone
            }
        }
    }
}