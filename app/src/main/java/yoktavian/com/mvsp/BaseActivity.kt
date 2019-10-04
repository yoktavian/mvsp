package yoktavian.com.mvsp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_modal.*
import yoktavian.com.mvsp.screen.user.UserDetailScreen

class BaseActivity : AppCompatActivity() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Start Fragment.
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frameContainer, UserDetailScreen.newInstance())
            .commit()

        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        messageModal.setOnClickListener {
            dismissModal()
        }
    }

        fun showModal(message: String) {
        messageModal.text = message
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun dismissModal() {
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }
}
