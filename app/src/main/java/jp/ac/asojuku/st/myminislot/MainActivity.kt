package jp.ac.asojuku.st.myminislot

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.AppLaunchChecker
import android.util.Log
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var bet: Int = 0;
    var mycoin: Int = 0;
    var resetCount:Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_button_reset.setOnClickListener { setmycoin(1000)
            setbet(0)
            resetCount++
        }
        main_button_betUp.setOnClickListener { betButtonTapped(0) }
        main_button_betDown.setOnClickListener { betButtonTapped(1) }
        main_button_start.setOnClickListener{
            val intent:Intent = Intent(this,GameActivity::class.java)
            intent.putExtra("mycoin",mycoin)
            intent.putExtra("bet",bet)
            if(resetCount>10){
                val intent2: Intent = Intent(this,MainActivity::class.java)
                val pref = PreferenceManager.getDefaultSharedPreferences(this)
                pref.edit().putInt("mycoin", 10000).commit()
                startActivity(intent2)
            }else {
                startActivity(intent)
            }
        }

    }

    fun betButtonTapped(flg: Int) {
        changeBet(flg)
    }

    fun changeBet(flg: Int) {
        if (flg == 0) {
            if (mycoin > 0) {
                setbet(bet+10)
                setmycoin(mycoin-10)
            }
        } else {
            if (bet > 0) {
                setbet(bet-10)
                setmycoin(mycoin+10)
            }
        }
    }
    fun setmycoin(value: Int){
        mycoin = value
        main_int_myCoin.text = mycoin.toString()
    }
    fun setbet(value: Int){
        bet = value
        main_int_bet.text = bet.toString()
    }

    override fun onResume() {
        super.onResume()
        var pref = PreferenceManager.getDefaultSharedPreferences(this)
        setmycoin(pref.getInt("mycoin", 1000))
        setbet(0)
        resetCount=0
        if (mycoin>=10000){
            main_img_center.setImageResource(R.drawable.clair)
        }

    }

}
