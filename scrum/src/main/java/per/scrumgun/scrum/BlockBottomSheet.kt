package per.scrumgun.scrum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import per.scrumgun.scrum.databinding.BlockBottomSheetBinding

class BlockBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BlockBottomSheetBinding
    private var mListener: OnBlockBottomSheetListener? = null

    companion object {
        fun newInstance(listener: OnBlockBottomSheetListener): BlockBottomSheet {
            val bottomSheet = BlockBottomSheet()
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
        binding = BlockBottomSheetBinding.inflate(inflater)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.closeImageView.setOnClickListener {
            dismiss()
        }
        binding.noneButton.setOnClickListener {
            mListener?.confirmNone(getString(R.string.block_bottom_sheet_none_label))
            dismiss()
        }
        binding.confirmButton.setOnClickListener {
            mListener?.confirmBlock(binding.workDetailEditText.text.toString())
            dismiss()
        }
    }

    private fun setListener(listener: OnBlockBottomSheetListener) {
        mListener = listener
    }

    interface OnBlockBottomSheetListener {
        fun confirmBlock(message: String)
        fun confirmNone(message: String)
    }
}
