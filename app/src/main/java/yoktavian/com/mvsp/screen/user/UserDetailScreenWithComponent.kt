package yoktavian.com.mvsp.screen.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import yoktavian.com.mvsp.base.BaseFragment
import yoktavian.com.mvsp.data.source.UserRepository

/**
 * Created by YudaOktavian on 17-Aug-2019
 */
class UserDetailScreenWithComponent :
    BaseFragment<UserDetailScreenWithComponent.State, UserDetailScreenWithComponent.Presenter>() {

    override val state = State()
    override val presenter = Presenter(state, this, UserRepository())

    class State {

    }

    class Presenter(state: State,
                    view: UserDetailScreenWithComponent,
                    repository: UserRepository
    ) : BaseFragment.Presenter<State, UserDetailScreenWithComponent, UserRepository>(state, view, repository) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}