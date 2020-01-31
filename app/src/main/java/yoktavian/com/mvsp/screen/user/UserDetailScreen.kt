package yoktavian.com.mvsp.screen.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.user_detail_layout.*
import yoktavian.com.mvsp.R
import yoktavian.com.mvsp.base.ui.BaseAdapter
import yoktavian.com.mvsp.base.ui.BaseFragment
import yoktavian.com.mvsp.component.SimpleModule
import yoktavian.com.mvsp.component.SimpleWithImgModule
import yoktavian.com.mvsp.data.User
import yoktavian.com.mvsp.data.source.UserRepository
import yoktavian.com.mvsp.helper.csText
import yoktavian.com.mvsp.helper.csVisibility

/**
 * Created by YudaOktavian on 03/02/2019
 */
class UserDetailScreen : BaseFragment<UserDetailScreen.State, UserDetailScreen.Presenter>() {
    /**
     * Just make the default State inside this class.
     * Everything that is make the state changes will determine
     * the View is updated. Every changes in State, make sure
     * to calling render.
     */
    class State {
        var isLoading = false
        var isNetworkError = false
        var userData: User? = null
    }

    /**
     * Presenter as default will have 3 params, that is State, View and Repository.
     * State and view is accessible in view, while repository is not. Repo is visible
     * just in Presenter. So if you needs to get data from Repository, make sure you calling
     * the Presenter first inside the View, fetch the result in the State, then calling
     * partial Render to update your View.
     * @param state
     * @param view
     * @param repository
     */
    class Presenter(
        state: State,
        view: UserDetailScreen,
        repository: UserRepository
    ) : BaseFragment.Presenter<State, UserDetailScreen, UserRepository>(state, view, repository) {

        fun fetchUserDetail() {
            UIjob {
                state.isLoading = true
                view.renderAll(state)
            }
            // get user detail from your API
            // region result
            IOjob {
                repository.getUserData { result ->
                    state.userData = result
                    state.isLoading = false
                    // rendering should using dispatcher main.
                    UIjob {
                        view.renderAll(state)
                    }
                }
            }
        }

        fun testingOne() {
            view.showToast("Testing one")
        }

        fun testingTwo() {
            view.showToast("Testing two")
        }
    }

    override fun initState() = State()
    override fun initPresenter(state: State) = Presenter(state, this, UserRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_detail_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.fetchUserDetail()
    }

    override fun renderAll(state: State) {
        /**
         * Call fragment to make sure this fragment
         * still alive to avoid memory leak or crash.
         */
        requireFragment {
            renderLoading(state)
            renderUserDetail(state)
            renderTestingAdapter()
        }
    }

    private fun renderUserDetail(state: State) {
        fragmentTitle.csText = state.userData?.name
    }

    /**
     * Every part of view have render method.
     * if any changes inside of State affected from Presenter,
     * of course it will determine view changes.
     * So you must calling partial render, based on
     * changes that occur in State.
     */
    override fun renderLoading(state: State) {
        // view of loading must be VISIBLE when state isLoading, otherwise GONE
        progressBar.csVisibility = View.VISIBLE.takeIf { state.isLoading } ?: View.GONE
    }

    override fun renderNetworkError(state: State) {
        if (state.isNetworkError) {
            // view of network error must be visible
        } else {
            // view of network error must be gone
        }
    }

    private fun renderTestingAdapter() {
        val listModul = listOf(
            SimpleModule(SimpleModule.createView(recyclerView))
                .bindState {
                    sampleText = "tes 1"
                },
            SimpleModule(SimpleModule.createView(recyclerView))
                .bindState {
                    sampleText = "tes 2"
                },
            SimpleWithImgModule(SimpleWithImgModule.createView(recyclerView))
                .bindState {
                    sampleText = "tes 2"
                    image = R.mipmap.ic_launcher
                    onClickListener = {
                        presenter.testingOne()
                    }
                },
            SimpleWithImgModule(SimpleWithImgModule.createView(recyclerView))
                .bindState {
                    sampleText = "apa aja cuy 2"
                    image = R.mipmap.ic_launcher
                    onClickListener = {
                        presenter.testingTwo()
                    }
                }
        )
        recyclerView.adapter = BaseAdapter(listModul)
    }

    companion object {
        fun newInstance() = UserDetailScreen()
    }
}