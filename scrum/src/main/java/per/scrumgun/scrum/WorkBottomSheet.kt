package per.scrumgun.scrum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import per.scrumgun.scrum.databinding.WorkBottomSheetBinding

class WorkBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: WorkBottomSheetBinding
    private var mListener: OnWorkBottomSheetListener? = null

    companion object {
        fun newInstance(listener: OnWorkBottomSheetListener): WorkBottomSheet {
            val bottomSheet = WorkBottomSheet()
            bottomSheet.setListener(listener)
            return bottomSheet
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WorkBottomSheetBinding.inflate(inflater)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.closeImageView.setOnClickListener {
            dismiss()
        }
        binding.confirmButton.setOnClickListener {
            mListener?.confirmWork(binding.workDetailEditText.text.toString())
            dismiss()
        }
    }

    private fun setListener(listener: OnWorkBottomSheetListener) {
        mListener = listener
    }

    interface OnWorkBottomSheetListener {
        fun confirmWork(message: String)
    }
}
