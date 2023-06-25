package per.scrumgun.scrum

import android.graphics.Color
import android.util.Log
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import org.koin.androidx.viewmodel.ext.android.viewModel
import per.scrumgun.core.base.BaseFragment
import per.scrumgun.core.base.viewBinding
import per.scrumgun.core.util.findNavControllerSafety
import per.scrumgun.core.view.ErrorDialog
import per.scrumgun.scrum.databinding.FragmentScrumBinding

class ScrumFragment : BaseFragment(R.layout.fragment_scrum),
    WorkBottomSheet.OnWorkBottomSheetListener,
    BlockBottomSheet.OnBlockBottomSheetListener {
    private val binding by viewBinding(FragmentScrumBinding::bind)
    private val mViewModel: ScrumViewModel by viewModel()
    private var mAdapter: ScrumAdapter? = null

    override fun setUpView() {
        mAdapter = ScrumAdapter()
        binding.scrumRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.scrumRecyclerView.adapter = mAdapter

        binding.backImageView.setOnClickListener {
            findNavControllerSafety(R.id.scrumFragment)?.popBackStack()
        }
        binding.workButton.setOnClickListener {
            WorkBottomSheet.newInstance(this).show(childFragmentManager, null)
        }
        binding.blockButton.setOnClickListener {
            BlockBottomSheet.newInstance(this).show(childFragmentManager, null)
        }
        binding.sendButton.setOnClickListener {
            mViewModel.sendMessage()
        }
    }

    override fun observeViewModel() {
        mViewModel.observeChats.observe(viewLifecycleOwner) {
            mAdapter?.submitList(it) {
                binding.scrumRecyclerView.scrollToPosition(it.size - 1)
            }
        }
        mViewModel.scrumViewModelProgress.observe(viewLifecycleOwner) {
            binding.scrumViewFlipper.displayedChild = if (it) 1 else 0
        }
        mViewModel.scrumViewModelFailedEvent.observe(viewLifecycleOwner) {
            ErrorDialog.newInstance(it).show(childFragmentManager, null)
        }
        mViewModel.isWorkMessageEmpty.observe(viewLifecycleOwner) {
            setBackgroundColorButton(it, binding.workButton)
        }
        mViewModel.isBlockMessageEmpty.observe(viewLifecycleOwner) {
            setBackgroundColorButton(it, binding.blockButton)
        }
        mViewModel.isMessageEmpty.observe(viewLifecycleOwner) {
            binding.sendButton.isEnabled = it
        }
        mViewModel.theme.observe(viewLifecycleOwner) {
            binding.scrumViewFlipper.setBackgroundColor(Color.parseColor(it.background))
        }
    }

    override fun confirmWork(message: String) {
        mViewModel.setWorkMessage(message)
    }

    override fun confirmBlock(message: String) {
        mViewModel.setBlockMessage(message)
    }

    override fun confirmNone(message: String) {
        mViewModel.setBlockMessage(message)
    }

    private fun setBackgroundColorButton(isMessageEmpty: Boolean, button: MaterialButton) {
        if (isMessageEmpty) {
            button.background.setTint(
                getColor(
                    requireContext(),
                    R.color.white
                )
            )
            button.setTextColor(requireContext().getColor(R.color.black))
        } else {
            button.background.setTint(
                getColor(
                    requireContext(),
                    R.color.black
                )
            )
            button.setTextColor(requireContext().getColor(R.color.white))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mAdapter = null
    }
}
