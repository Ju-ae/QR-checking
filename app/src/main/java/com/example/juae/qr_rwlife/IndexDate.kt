package com.example.juae.qr_rwlife

import java.util.*

class IndexDate{

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
        if(rhour < 10) {
            rhour_s = "0$rhour_s"
        }
        if(rmin<10)
        {
            rmin_s = "0$rmin_s"
        }
        if(rsec< 10){
            rsec_s = "0$rsec_s"
        }

        returnStamp = ryear_s + "-" + rmonth_s + "-" + rdate_s+"T"+rhour_s+":"+rmin_s+":"+rsec_s;

        return returnStamp
    }


    fun getDateText() : String {
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

        returnStamp = ryear_s + "년 " + rmonth_s + "월 " + rdate_s+"일"

        return returnStamp
    }

    fun getTimeText(): String {
        var returnStamp = ""
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR_OF_DAY, 9)

        var rhour = calendar.get(Calendar.HOUR)
        var rmin = calendar.get(Calendar.MINUTE)
        var rsec = calendar.get(Calendar.SECOND)

        var rhour_s = rhour.toString()
        var rmin_s = rmin.toString()
        var rsec_s = rsec.toString()

        if(rhour < 10) {
            rhour_s = "0$rhour_s"
        }
        if(rmin<10)
        {
            rmin_s = "0$rmin_s"
        }
        if(rsec< 10){
            rsec_s = "0$rsec_s"
        }

        returnStamp = rhour_s+"시 "+rmin_s+"분 "+rsec_s+"초 "

        return returnStamp
    }
}