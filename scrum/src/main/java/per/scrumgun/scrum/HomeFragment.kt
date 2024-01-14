package per.scrumgun.scrum

import android.graphics.Color
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel
import per.scrumgun.core.GoogleLogin.SERVER_CLIENT_ID
import per.scrumgun.core.base.BaseFragment
import per.scrumgun.core.base.viewBinding
import per.scrumgun.core.util.findNavControllerSafety
import per.scrumgun.core.view.ErrorDialog
import per.scrumgun.scrum.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val mViewModel: HomeViewModel by viewModel()
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(SERVER_CLIENT_ID)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    override fun setUpView() {
        binding.logoutButton.setOnClickListener {
            mAuth = FirebaseAuth.getInstance()
            mGoogleSignInClient.signOut()
            mAuth.signOut()
            mViewModel.logout()
            findNavControllerSafety(R.id.homeFragment)?.navigate(
                HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            )
        }
        binding.checkUpButton.setOnClickListener {
            findNavControllerSafety(R.id.homeFragment)?.navigate(
                HomeFragmentDirections.actionHomeFragmentToScrumFragment()
            )
        }
    }

    override fun observeViewModel() {
        mViewModel.homeProgress.observe(viewLifecycleOwner) {
            binding.homeViewFlipper.displayedChild = if (it) 1 else 0
        }
        mViewModel.user.observe(viewLifecycleOwner) {
            binding.nameTextView.text = it.name
            binding.emailTextView.text = it.email
        }
        mViewModel.homeFailedEvent.observe(viewLifecycleOwner) {
            ErrorDialog.newInstance(it).show(childFragmentManager, null)
            mAuth = FirebaseAuth.getInstance()
            mAuth.signOut()
            mGoogleSignInClient.signOut()
        }
        mViewModel.theme.observe(viewLifecycleOwner) {
            binding.homeViewFlipper.setBackgroundColor(Color.parseColor(it.background))
            binding.nameTextView.setTextColor(Color.parseColor(it.text))
            binding.emailTextView.setTextColor(Color.parseColor(it.text))
        }
    }
}
