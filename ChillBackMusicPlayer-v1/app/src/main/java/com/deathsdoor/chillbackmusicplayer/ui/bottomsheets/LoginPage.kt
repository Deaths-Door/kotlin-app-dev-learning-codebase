package com.deathsdoor.chillbackmusicplayer.ui.bottomsheets

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.deathsdoor.chillbackmusicplayer.data.Constants
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.showToast
import com.deathsdoor.chillbackmusicplayer.data.extensions.UI.text
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.ClickListeners.onClick
import com.deathsdoor.chillbackmusicplayer.data.firebase.FireAuthHelper
import com.deathsdoor.chillbackmusicplayer.data.firebase.GoogleLogin
import com.deathsdoor.chillbackmusicplayer.data.viewmodels.MainViewModel
import com.deathsdoor.chillbackmusicplayer.databinding.BottomsheetLoginPageBinding
import com.deathsdoor.ui_core.public.PreferenceExtensions.changePreference
import com.deathsdoor.ui_core.public.PreferenceExtensions.sharedPreference
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LoginPage : BottomSheetDialogFragment(){
    companion object {
        const val TAG = "BottomsheetLoginBinding"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { bottomSheet ->
                val behaviour = BottomSheetBehavior.from(bottomSheet)
                val layoutParams = bottomSheet.layoutParams
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
                bottomSheet.layoutParams = layoutParams
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private lateinit var _binding: BottomsheetLoginPageBinding
    private val googleLogin by lazy { GoogleLogin(requireActivity()) }
    private val mainViewmodel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomsheetLoginPageBinding.inflate(inflater,container,false)
        return _binding.root
    }

    private lateinit var sharedPreferences: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewmodel.loginPageShown = true

        sharedPreferences = requireContext().sharedPreference(Constants.SETTING_PREFERENCE)!!

        _binding.emailPasswordLogin.onClick {
            FireAuthHelper.startEmailPasswordLogin(_binding.email.editText!!.text(), _binding.pwd.editText!!.text(),
                object : FireAuthHelper.OnLoginAttemptListener{
                    override fun onEmailInValid(email: String) = requireContext().showToast("Invalid Email")
                    override fun onPasswordIsWeak(password: String) = requireContext().showToast("Password is not strong enough")
                    override fun onSuccess() {
                        requireContext().showToast("Login Successful")
                        sharedPreferences.changePreference(Constants.SETTING.FIREBASE_AUTH_CURRENT_USER.name,FireAuthHelper.currentUser!!.uid)
                    }
                    override fun onFailure() = requireContext().showToast("Login Failed. Try Again")
                }
            )
        }

        _binding.forgotPassword.onClick {
            val email =_binding.email.editText!!.text()
            FireAuthHelper.resetPasswordEmail(email,object :FireAuthHelper.OnLoginAttemptListener{
                    override fun onEmailInValid(email: String) = requireContext().showToast("Invalid Email")
                    override fun onPasswordIsWeak(password: String) = Unit
                    override fun onSuccess() = requireContext().showToast("Password reset email sent to $email")
                    override fun onFailure() = requireContext().showToast("Error sending password reset email. Try Again")
                }
            )
        }

        _binding.googleSignIn.onClick { googleLogin.initiateSignIn() }
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googleLogin.handleSignInResult(requestCode,data) { isSuccessful, message ->
            if(isSuccessful) {
                requireContext().showToast("Sign-In Successful")
                sharedPreferences.changePreference(Constants.SETTING.FIREBASE_AUTH_CURRENT_USER.name,FireAuthHelper.currentUser!!.uid)
                // dismiss()
            }
            else requireContext().showToast("There was a problem logging into your account. Please try again later")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewmodel.loginPageShown = false
    }
}