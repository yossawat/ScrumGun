package per.scrumgun

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import per.scrumgun.core.util.toast

class MainActivity : AppCompatActivity() {
    private val mViewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.FoodStory)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeViewModel()
    }


    private fun observeViewModel() {
        mViewModel.getThemeFailedEvent.observe(this) {
            toast(it)
        }
    }
}
