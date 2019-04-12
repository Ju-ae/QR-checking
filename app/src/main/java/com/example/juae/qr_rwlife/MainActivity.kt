package com.example.juae.qr_rwlife

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    val TAG: String = "MAINACTIVITY"

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        setContentView(R.layout.activity_main)
        var view: View = View(this)

        qrstart_btn.setOnClickListener { View ->
            qrScanStart()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val userD: SharedPreferences = getSharedPreferences("user", MODE_PRIVATE) // a라는 sharedpreferences를 얻음
        var qr: String = userD.getString("qr", "")
        var intentResult: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (!"".equals(qr)) {
            if (intentResult != null) {
                if (intentResult.contents == null) {
                //    Toast.makeText(this, "Scan Cancelled ", Toast.LENGTH_LONG).show()
                } else {
                    if (intentResult.contents.toString().equals(qr)) {
                        Toast.makeText(this, intentResult.contents, Toast.LENGTH_LONG).show()
                        val intent = Intent(this, CheckdoneActivity::class.java)
                        inputdb()
                        startActivity(intent)
                    } else {
                        qrScanStart()
                    }
                }
            }
        } else {
            Log.e(TAG, "qr code is null");
            //super.onActivityResult(requestCode, resultCode, data)

        }
    }


    fun qrScanStart() {
        var i: IntentIntegrator = IntentIntegrator(this)
        i.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        i.setPrompt("QR코드를 스캔해주세요")
        i.setCaptureActivity(QrscanActivity::class.java)
        i.setCameraId(0)
        i.setOrientationLocked(false)
        i.setBeepEnabled(true)
        i.setBarcodeImageEnabled(true)
        i.initiateScan()
    }

    fun getDate(): String {
        var returnStamp = ""
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR_OF_DAY, 9)
        val ryear = calendar.get(Calendar.YEAR)
        val rmonth = calendar.get(Calendar.MONTH) + 1
        val rdate = calendar.get(Calendar.DAY_OF_MONTH)
        val ryear_s = ryear.toString()
        var rmonth_s = rmonth.toString()
        if (rmonth < 10) {
            rmonth_s = "0$rmonth_s"
        }
        var rdate_s = rdate.toString()
        if (rdate < 10) {
            rdate_s = "0$rdate_s"
        }

        returnStamp = ryear_s + "-" + rmonth_s + "-" + rdate_s

        return returnStamp
    }

    fun getTimestamp(): String {
        var returnStamp = ""
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR_OF_DAY, 9)
        val ryear = calendar.get(Calendar.YEAR)
        val rmonth = calendar.get(Calendar.MONTH) + 1
        val rdate = calendar.get(Calendar.DAY_OF_MONTH)
        val ryear_s = ryear.toString()
        var rmonth_s = rmonth.toString()
        var rhour = calendar.get(Calendar.HOUR)
        var rmin = calendar.get(Calendar.MINUTE)
        var rsec = calendar.get(Calendar.SECOND)

        var rhour_s = rhour.toString()
        var rmin_s = rmin.toString()
        var rsec_s = rsec.toString()

        if (rmonth < 10) {
            rmonth_s = "0$rmonth_s"
        }
        var rdate_s = rdate.toString()

        if (rdate < 10) {
            rdate_s = "0$rdate_s"
        }
        if (rhour < 10) {
            rhour_s = "0$rhour_s"
        }
        if (rmin < 10) {
            rmin_s = "0$rmin_s"
        }
        if (rsec < 10) {
            rsec_s = "0$rsec_s"
        }

        returnStamp = ryear_s + "-" + rmonth_s + "-" + rdate_s + "T" + rhour_s + ":" + rmin_s + ":" + rsec_s;

        return returnStamp
    }

    fun startCheckdoneActivity(view: View) {
        val intent = Intent(this, CheckdoneActivity::class.java)
        startActivity(intent)
    }

    fun inputdb() {
        val userD: SharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val docRef: DocumentReference = db.collection("check").document(getDate()).collection(userD.getString("group", "")).document(getTimestamp())
        Log.e(TAG, "docRef : " + docRef)
        var data: CheckData = CheckData(userD.getString("name", ""), userD.getString("group", ""), userD.getString("uid", ""), userD.getBoolean("ismale", true), Date(System.currentTimeMillis()))
        docRef.set(data)
        // Update the timestamp field with the value from the server
        //  val updates = HashMap()
        // updates.put("timestamp", FieldValue.serverTimestamp())


    }



/*
        db.collection("user")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            Log.d(TAG, document.id + " => " + document.data)
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.exception)
                    }
                }*/
    //       val user = HashMap()
    //   user.put("first", "Ada")
    //    user.put("last", "Lovelace")
    // user.put("born", 1815)


/*

// Add a new document with a generated ID
        db.collection("user")
                .add(jueun)
                .addOnSuccessListener { documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.id)
                    val uid = HashMap<String, String>()
                    uid.put("uid",documentReference.id)
                    db.collection("user").document(documentReference.id).update("uid", documentReference.id)
                }
                .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e)
                }*/
}

