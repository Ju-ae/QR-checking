package com.example.juae.qr_rwlife

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    val TAG: String = "LOGIN_ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.e(TAG, "loginActivity");
        var btnLogin = findViewById(R.id.btnLogin)  as Button


        btnLogin.setOnClickListener { View ->
            Log.e(TAG, "id : " + editTextId.getText())
       //     Log.e(TAG, "pw : " + editTextPw.getText())
            matchLogin(editTextId.text.toString(), editTextPw.text.toString())
            editTextId.setText("")
            editTextPw.setText("")
        }
    }

    fun matchLogin(id: String, pw: String) {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var colRef: CollectionReference = db.collection("user")
        var flag: Boolean = false
        //Log.e(TAG, "enter matchLogin")
        db.collection("user")
                .whereEqualTo("id", id)
                .whereEqualTo("passwd", pw)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            Log.d(TAG, document.id + " => " + document.get("id"))
                            flag = true
                            val userD : SharedPreferences = getSharedPreferences("user", MODE_PRIVATE) // a라는 sharedpreferences를 얻음
                            val userEdit :  SharedPreferences.Editor = userD.edit()
                            userEdit.putBoolean("autoLogin", true)
                            userEdit.putString("uid", document.get("uid").toString())
                            userEdit.putString("birth", document.get("birth").toString())
                            userEdit.putString("id", document.get("id").toString())
                            userEdit.putString("name", document.get("name").toString())
                            userEdit.putString("passwd", document.get("passwd").toString())
                            userEdit.putString("group", document.get("group").toString())
                            userEdit.putBoolean("ismale", document.get("ismale") as Boolean)
                            userEdit.commit()
                            Toast.makeText(applicationContext, "LoginSuccesed", Toast.LENGTH_LONG).show()

                            startMainActivity()
                            this.finish()
                        }
                        if(!flag){
                            Toast.makeText(applicationContext, "로그인 실패", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.exception)
                        Toast.makeText(applicationContext, "LoginFailed", Toast.LENGTH_LONG).show()
                    }
                }
    }

    fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
