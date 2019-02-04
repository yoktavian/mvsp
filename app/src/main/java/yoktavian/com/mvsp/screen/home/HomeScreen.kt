package yoktavian.com.mvsp.screen.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import yoktavian.com.mvsp.R

/**
 * Created by YudaOktavian on 04/02/2019
 */
class HomeScreen : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_sreen_layout, container, false)
    }

    companion object {
        fun newInstance() = HomeScreen()
    }
}