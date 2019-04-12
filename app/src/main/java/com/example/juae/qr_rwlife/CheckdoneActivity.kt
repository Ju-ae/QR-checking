package com.example.juae.qr_rwlife

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_checkdone.*

class CheckdoneActivity: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        var date = IndexDate()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkdone)

        val userD : SharedPreferences = getSharedPreferences("user", MODE_PRIVATE) // a라는 sharedpreferences를 얻음

        var name : String = userD.getString("name", "")
        var group : String = userD.getString("group", "")

        textview_name.setText(group+" " + name)
        textview_date.setText(date.getDateText())
        textview_time.setText(date.getTimeText())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}