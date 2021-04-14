package com.hhi.tripproject.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hhi.tripproject.BuildConfig
import com.hhi.tripproject.R
import com.hhi.tripproject.base.BaseActivity
import com.hhi.tripproject.databinding.ActivityLoginBinding
import com.hhi.tripproject.global.App
import com.hhi.tripproject.view.main.MainActivity
import com.hhi.tripproject.viewmodel.LoginViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.auth.model.Prompt
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val vm: LoginViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager
    private lateinit var buttonFacebookLogin: LoginButton
    private lateinit var buttonGoogleLogin: SignInButton
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        binding.vm = vm

        // Check AutoLogin
        if (vm.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        setObserver()
        init()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun init() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth
        callbackManager = CallbackManager.Factory.create()
        buttonFacebookLogin = findViewById(R.id.login_facebook)
        buttonGoogleLogin = findViewById(R.id.login_google)
        buttonGoogleLogin.setOnClickListener {
            startActivityForResult(
                googleSignInClient.signInIntent,
                RC_SIGN_IN
            )
        }
    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    vm.saveTokenToServer(
                        token = idToken,
                        provider = "G",
                        email = auth.currentUser.email
                    )
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    vm.saveTokenToServer(
                        token = token.toString(),
                        provider = "F",
                        email = auth.currentUser.email
                    )
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun setObserver() {
        vm.loginSuccessEvent.observe(this, Observer {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })

        vm.loginFailedEvent.observe(this, Observer {
            showToast(it)
        })

        vm.signUpClickEvent.observe(this, Observer {
            startActivity(Intent(this, SignUpActivity::class.java))
        })

        vm.naverLoginClickEvent.observe(this, Observer {
            val mOAuthLoginInstance = OAuthLogin.getInstance()
            mOAuthLoginInstance.init(
                App.context,
                BuildConfig.NAVER_CLIENT_ID,
                BuildConfig.NAVER_CLIENT_SECRET,
                R.string.app_name.toString()
            )

            mOAuthLoginInstance.startOauthLoginActivity(this, @SuppressLint("HandlerLeak")
            object : OAuthLoginHandler() {
                override fun run(success: Boolean) {
                    if (success) {
                        val accessToken: String =
                            mOAuthLoginInstance.getAccessToken(this@LoginActivity)
                        vm.getNaverEmail(accessToken)
                    } else {
                        val errorCode: String =
                            mOAuthLoginInstance.getLastErrorCode(this@LoginActivity).code
                        val errorDesc =
                            mOAuthLoginInstance.getLastErrorDesc(this@LoginActivity)
                        Log.d(
                            TAG,
                            "naverLoginFailed:errorCode: $errorCode, errorDesc: $errorDesc"
                        )
                    }
                }
            })
        })

        vm.kakaoLoginClickEvent.observe(this, Observer {
            var email: String = ""
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e(TAG, "kakaoLoginfailed:")
                } else if (token != null) {

                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.e(TAG, "사용자 정보 요청 실패", error)
                        } else {
                            if (user?.kakaoAccount?.email != null) {
                                email = user?.kakaoAccount?.email!!
                                Log.i(TAG, "사용자 정보 요청 성공 이메일: ${user?.kakaoAccount?.email}")
                            } else {
                                var scopes = mutableListOf<String>()
                                if (user?.kakaoAccount?.emailNeedsAgreement == true) {
                                    scopes.add("account_email")
                                }
                                if (scopes.count() > 0) {
                                    Log.d(TAG, "사용자에게 추가 동의를 받아야 합니다.")

                                    UserApiClient.instance.loginWithNewScopes(
                                        this@LoginActivity,
                                        scopes
                                    ) { token, error ->
                                        if (error != null) {
                                            Log.e(TAG, "사용자 추가 동의 실패", error)
                                        } else {
                                            Log.d(TAG, "allowed scopes: ${token!!.scopes}")

                                            // 사용자 정보 재요청
                                            UserApiClient.instance.me { user, error ->
                                                if (error != null) {
                                                    Log.e(TAG, "사용자 정보 요청 실패", error)
                                                } else if (user != null) {
                                                    email = user?.kakaoAccount?.email!!
                                                    Log.i(
                                                        TAG,
                                                        "사용자 정보 요청 성공 이메일: ${user.kakaoAccount?.email}"
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            vm.saveTokenToServer(
                                token = token.accessToken,
                                provider = "K",
                                email = email
                            )

                        }
                    }

                }
            }

            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@LoginActivity)) {

                UserApiClient.instance.loginWithKakaoTalk(this@LoginActivity, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(
                    this@LoginActivity,
                    prompts = listOf(Prompt.LOGIN),
                    callback = callback
                )
            }
        })

        vm.facebookLoginClickEvent.observe(this, Observer {
            buttonFacebookLogin.setReadPermissions("email", "public_profile")
            buttonFacebookLogin.registerCallback(callbackManager, object :
                FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d(TAG, "facebook:onSuccess:$loginResult")
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    Log.d(TAG, "facebook:onCancel")
                }

                override fun onError(error: FacebookException) {
                    Log.d(TAG, "facebook:onError", error)
                }
            })
        })
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val RC_SIGN_IN = 9001
    }
}