package com.daisy.picky

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.daisy.picky.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.network.KakaoRetrofitConverterFactory
import com.kakao.sdk.user.UserApiClient


class LoginActivity : BaseActivity() {

    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    var firebaseAuth: FirebaseAuth? = null
    val GOOGLE_REQUEST_CODE = -1
    val TAG = "googleLogin"

    lateinit var kakaoApplication: KakaoApplication

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var keyHash = Utility.getKeyHash(this)
        Log.v(TAG, keyHash)

        prefs = this.getSharedPreferences("login", Context.MODE_PRIVATE)

        //Google-Login Initialize
        FirebaseApp.initializeApp(this)
        firebaseAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_API_KEY)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //TODO: Kakao-Login Initialize

        //TODO : Naver-Login Initialize


        binding.btnGoogleLogin.setOnClickListener {
            Log.d(TAG, "Google- Start Login")
            GoogleSignIn()
        }

        binding.btnKakaoLogin.setOnClickListener {
            KaKaoSignIn()
            /*
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Toast.makeText(this, "카카오톡이 설치되어 있지 않습니다.", Toast.LENGTH_SHORT).show()
                } else if (token != null) {
                    Log.d(TAG, "로그인 성공 ${token.accessToken}")
                }
            }*/
        }


    }



    override fun onStart() {
        getLoginInfo()

        Log.d(TAG, preLoginId)
        if(preLoginMethod == GOOGLE_LOGIN)
        {
            Log.d(TAG, "Google - Auto Login")
            if(firebaseAuth!!.currentUser != null) {
                firebaseAuth!!.currentUser?.getIdToken(true)!!.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val idToken = task.result.token
                        Log.d(TAG, idToken!!)
                        setLoginInfo(GOOGLE_LOGIN, idToken)
                        loginSuccess()
                    }
                }
            }

        }
        else if(preLoginMethod == KAKAO_LOGIN){
            Log.d(TAG, "Kakao - Auto Login")
            if (AuthApiClient.instance.hasToken()) {
                UserApiClient.instance.accessTokenInfo { _, error ->
                    if (error != null) {
                        if (error is KakaoSdkError && error.isInvalidTokenError() == true) {
                            // 로그인 필요
                        }
                        else {
                            //기타 에러
                        }
                    }
                    else {
                        loginSuccess()
                    }
                }
            }
            else {
                //로그인 필요
            }

        }
        else if(preLoginMethod == NAVER_LOGIN){

        }

        super.onStart()
    }



    private fun GoogleSignIn() {
        Log.d(TAG, "Google- Call Sign In Pop-up")
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
        //deprecated
        //startActivityForResult(signInIntent, GOOGLE_REQUEST_CODE)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Log.d(TAG, "Google- Receive Sign In Result"+result.resultCode)
        if (result.resultCode == GOOGLE_REQUEST_CODE) {
            Log.d(TAG, "Google- Receive Sign In Result")
            val data: Intent? = result.data
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>){
        try{
            val account = completedTask.getResult(ApiException::class.java)!!
            Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
            firebaseAuthWithGoogle(account.idToken!!)
        }
        catch (e: ApiException){
            Log.w(TAG,"signInResult:Fail code = " + e.statusCode)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "로그인 성공")
                    //val user = firebaseAuth!!.currentUser
                    //checkIsNewUser(user)
                    setLoginInfo(GOOGLE_LOGIN, idToken)
                    loginSuccess()
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun checkIsNewUser(curUser: FirebaseUser?) {

        /*

        curUser?.let {
            CurrentUser.uid = curUser.uid

            val docRef = db.collection("users").document(CurrentUser.uid)
            docRef.get()
                .addOnSuccessListener { document ->
                    Log.d(TAG, "유저 로그인 기록 조회 성공")
                    if (document.exists()){
                        CurrentUser.email = document.data!!.get("email").toString()
                        CurrentUser.name = document.data!!.get("name").toString()
                        CurrentUser.profile = document.data!!.get("profile").toString()
                        CurrentUser.englishName = document.data!!.get("englishName").toString()
                        CurrentUser.birth = document.data!!.get("birth").toString()
                        CurrentUser.departure = document.data!!.get("departure").toString()
                        CurrentUser.location = document.data!!.get("location").toString()
                        loginSuccess()
                    }
                    else{
                        CurrentUser.email = curUser.email
                        CurrentUser.name = curUser.displayName
                        CurrentUser.profile = curUser.photoUrl.toString()
                        loginSuccessforNewUsers()
                    }
                }
                .addOnFailureListener{ exception ->
                    Log.d(TAG, "get failed with ", exception)
                }

        }

         */


    }
    private fun loginSuccessforNewUsers(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loginSuccess(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setLoginInfo(method: Int, token: String){
        preLoginMethod = method
        preLoginId = token
        prefs.edit().putInt("method", preLoginMethod).apply()
        prefs.edit().putString("token", preLoginId).apply()
    }
    private fun getLoginInfo(){
        preLoginMethod = prefs.getInt("method", 0)
        preLoginId = prefs.getString("token", "").toString()
    }

    // Kakao 이메일 로그인 콜백
    private val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        Log.e(TAG, "getCallback")
        if (error != null) {
            Log.e(TAG, "로그인 실패 $error")
        } else if (token != null) {
            Log.e(TAG, "로그인 성공 ${token.accessToken}")
            setLoginInfo(KAKAO_LOGIN, token.accessToken)
            loginSuccess()
        }
    }

    private fun KaKaoSignIn() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = kakaoCallback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = kakaoCallback)
        }
    }

}