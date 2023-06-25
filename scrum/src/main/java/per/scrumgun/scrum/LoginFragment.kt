package per.scrumgun.scrum

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import per.scrumgun.core.GoogleLogin.REQ_CODE
import per.scrumgun.core.GoogleLogin.SERVER_CLIENT_ID
import per.scrumgun.core.base.BaseFragment
import per.scrumgun.core.base.viewBinding
import per.scrumgun.core.util.findNavControllerSafety
import per.scrumgun.core.view.ErrorDialog
import per.scrumgun.domain.model.mapper.NetworkThemeToThemeMapper
import per.scrumgun.scrum.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment(R.layout.fragment_login) {
    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val mViewModel: LoginViewModel by viewModel()
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
        binding.signInButton.setOnClickListener {
            loginWithGoogle()
        }
    }

    private fun loginWithGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, REQ_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mViewModel.loginWithGoogle(requestCode, data)
    }

    override fun observeViewModel() {
        mViewModel.navigateToHomeFragmentEvent.observe(viewLifecycleOwner) {
            findNavControllerSafety(R.id.loginFragment)?.navigate(
                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            )
        }
        mViewModel.loginFailedEvent.observe(viewLifecycleOwner) {
            ErrorDialog.newInstance(it).show(childFragmentManager, null)
        }
        mViewModel.theme.observe(viewLifecycleOwner) {
            binding.loginLayout.setBackgroundColor(Color.parseColor(it.background))
            binding.logoTextView.setTextColor(Color.parseColor(it.text))
        }
    }
}
