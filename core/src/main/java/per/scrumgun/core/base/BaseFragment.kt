package per.scrumgun.core.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(
    @LayoutRes
    contentLayoutId: Int,
) : Fragment(contentLayoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            extractExtras(it)
        }
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState)
        }

        setUpInstance()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        observeViewModel()
        if (savedInstanceState == null) {
            init()
        } else {
            restoreView(savedInstanceState)
        }
    }

    /**
     * Extract extras from fragment's arguments
     * called when fragment has arguments set.
     */
    open fun extractExtras(arguments: Bundle) {        //Optional
    }

    /**
     * Instantiate object (e.g., Adapter, Handler)
     * Call after [com.wongnai.core.base.BaseFragment.bindView]
     */
    open fun setUpInstance() {        //Optional
    }

    /**
     * Setup view related things (e.g., View.setOnClickListener, recyclerView setUp)
     * Call after [com.wongnai.core.base.BaseFragment.bindView] and [com.wongnai.core.base.BaseFragment.setUpInstance]
     */
    open fun setUpView() {        //Optional
    }

    /**
     * Use to observe ViewModel's LiveData.
     */
    open fun observeViewModel() {        //Optional
    }

    /**
     * Do things when fragment first created.
     */
    open fun init() {        //Optional
    }

    /**
     * Restore savedInstanceState data.
     * Called when configuration change.
     */
    open fun onRestoreInstanceState(savedInstanceState: Bundle?) {        //Optional
    }

    /**
     * Restore view's state from savedInstanceState.
     * Called when configuration change.
     */
    open fun restoreView(savedInstanceState: Bundle?) {        //Optional
    }
}
