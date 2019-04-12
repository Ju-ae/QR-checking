package com.example.juae.qr_rwlife;

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore


class LodingActivity : AppCompatActivity() {

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val TAG : String = "LODING_ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loding)
        val userD : SharedPreferences = getSharedPreferences("user", MODE_PRIVATE) // a라는 sharedpreferences를 얻음
        val userEdit :  SharedPreferences.Editor = userD.edit()

        db.collection("qr").document("qr")
                .get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.data)
                           // val qr: String = intent.getStringExtra("qr")
                            var qr : String = document.data.get("code").toString()
                            var autoLogin: Boolean = userD.getBoolean("autoLogin", false)
                            userEdit.putString("qr", qr)
                            userEdit.commit()

                            if (!autoLogin) { // auto, login flag가 true가 아니면 로그인 창 띄움
                                Log.e(TAG, "!autologin")
                                startLoginActivity()
                                finish()
                            } else {
                                Log.e(TAG, "autologin")
                                autoLogin()
                            }

                        } else {
                            Log.d(TAG, "No such document")
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.exception)
                    }
                }
    }

    fun autoLogin(){
        Log.e(TAG, "autologin");
        val userD : SharedPreferences = getSharedPreferences("user", MODE_PRIVATE) // a라는 sharedpreferences를 얻음
        val userEdit :  SharedPreferences.Editor = userD.edit()
        var id : String = userD.getString("id", "")
        var pw : String = userD.getString("passwd", "")
        var i : Int  = 0
        if(!"".equals(id) && !"".equals(pw)){
            db.collection("user")
                    .whereEqualTo("id", id)
                    .whereEqualTo("passwd", pw)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (document in task.result) {
                                Log.d(TAG, document.id + " => " + document.get("id"))
                                 Toast.makeText(applicationContext, "LoginSucessed", Toast.LENGTH_LONG).show()
                                i += 1
                                userEdit.putBoolean("autoLogin", true)
                                userEdit.putString("uid", document.get("uid").toString())
                                userEdit.putString("birth", document.get("birth").toString())
                                userEdit.putString("id", document.get("id").toString())
                                userEdit.putString("name", document.get("name").toString())
                                userEdit.putString("passwd", document.get("passwd").toString())
                                userEdit.putString("group", document.get("group").toString())
                                userEdit.putBoolean("ismale", document.get("ismale") as Boolean)
                                userEdit.apply()
                                startMainActivity()
                                this.finish()
                            }
                            if(i != 1){
                                Toast.makeText(applicationContext, "LoginFailed", Toast.LENGTH_LONG).show()
                                userEdit.remove("autoLogin")
                                userEdit.remove("uid")
                                userEdit.remove("birth")
                                userEdit.remove("id")
                                userEdit.remove("name")
                                userEdit.remove("passwd")
                                userEdit.remove("group")
                                userEdit.remove("ismale")
                                userEdit.commit()
                                startLoginActivity()
                                this.finish()
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.exception)
                            Toast.makeText(applicationContext, "LoginFailed", Toast.LENGTH_LONG).show()
                            this.finishAffinity()
                        }
                    }
        }else{
            Log.e(TAG, "id&pw need");
            /*startLoginActivity()
            this.finish()
       */
        }
    }

    fun startLoginActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}
