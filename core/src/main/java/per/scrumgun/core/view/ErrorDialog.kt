package per.scrumgun.core.view

import android.os.Bundle
import per.scrumgun.core.R
import per.scrumgun.core.base.BaseDialogFragment
import per.scrumgun.core.base.viewBinding
import per.scrumgun.core.databinding.FragmentDialogErrorBinding

class ErrorDialog : BaseDialogFragment(R.layout.fragment_dialog_error) {
    private val binding by viewBinding(FragmentDialogErrorBinding::bind)
    private var mError: String? = null

    companion object {
        private const val EXTRA_ERROR = "extra-error"

        fun newInstance(error: String): ErrorDialog {
            return ErrorDialog().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_ERROR, error)
                }
            }
        }
    }

    override fun extractExtras(arguments: Bundle) {
        mError = arguments.getString(EXTRA_ERROR)
    }

    override fun setUpView() {
        binding.errorCodeTestView.text = mError

        binding.confirmButton.setOnClickListener {
            dismiss()
        }
    }
}
