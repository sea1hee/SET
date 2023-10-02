package com.daisy.picky

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.daisy.picky.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.util.SharedPreferencesUtils
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class LoginActivity : BaseActivity() {

    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    var firebaseAuth: FirebaseAuth? = null
    val GOOGLE_REQUEST_CODE = -1
    val TAG = "googleLogin"

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        prefs = this.getSharedPreferences("login", Context.MODE_PRIVATE)

        //Google-Login Initialize
        FirebaseApp.initializeApp(this)
        firebaseAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.firebase_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //TODO: Kakao-Login Initialize

        //TODO : Naver-Login Initialize


        binding.btnGoogleLogin.setOnClickListener {
            Log.d(TAG, "Google- Start Login")
            GoogleSignIn()
        }

    }

    override fun onStart() {
        getLoginInfo()

        Log.d(TAG, preLoginId)
        if(preLoginMethod == GOOGLE_LOGIN)
        {
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

}