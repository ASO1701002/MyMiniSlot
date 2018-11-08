package jp.ac.asojuku.st.myminislot

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_game.*


class GameActivity : AppCompatActivity() {
    var slotTapped:Int = 0

    var bet:Int = 0
    var mycoin:Int = 0
//タップされたスロットの数

    var slotArray:Array<GameActivity.slotButton?> = arrayOfNulls(3)

    //全スロットタップ後の当たりの判定
    fun checkHit():Int{
        var slot1:Int? = slotArray[0]?.imageNum
        var slot2:Int? = slotArray[1]?.imageNum
        var slot3:Int? = slotArray[2]?.imageNum
        var rate:Int = 0
        if(slot1==slot2 && slot2==slot3){
            when(slot1){
                7 -> rate = 20
                2 -> rate = 10
                1 -> rate = 5
                else -> 2
            }
        }else if(slot1==slot2 || slot1==slot3 || slot2==slot3){
            if(slot1==7 && slot2==7 || slot1==7 && slot3==7 || slot2==7 && slot3==7){
                rate = 3
            }else{
                rate = 1;
            }
        }else if(slot1==8 || slot2==8 || slot3==8){
            rate = 1
        }else if(slot1==6 && slot2==3 && slot3==5){
            rate = 30
        }else if(slot1==8 && slot2==0 && slot3==4){
            rate = 10
        }
        return rate
    }
    fun result(){
        var rate:Int = checkHit()
        var get:Int = bet*rate
        mycoin += get
        if(rate>0) {
            act_text_result.text = (get.toString() + "獲得！")
            act_image_center.setImageResource(R.drawable.peterpan)
            act_text_resultMsg.text = "ヒャッハー！"
        }else{
            act_text_result.text = "外れ！"
            act_image_center.setImageResource(R.drawable.hemingway)
            act_text_resultMsg.text = "　・・・"
        }
    }
    class slotButton(id:ImageButton) {
        var imgArray = arrayOf(
                R.drawable.banana,
                R.drawable.bar,
                R.drawable.bigwin,
                R.drawable.cherry,
                R.drawable.grape,
                R.drawable.lemon,
                R.drawable.orange,
                R.drawable.seven,
                R.drawable.waltermelon
        )
        var imageNum: Int = -1
        var id: ImageButton = id
    }

    fun setImage(slotButton: slotButton ?){
        if(slotButton?.imageNum==-1) {
            var random: Int = java.util.Random().nextInt(9)
            slotButton.id.setImageResource(slotButton.imgArray[random])
            slotButton.imageNum = random
            slotTapped++
            if(slotTapped>2){
                result()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        //mycoin,betの表示
        bet = intent.extras.getInt("bet")
        mycoin = intent.extras.getInt("mycoin")
        act_int_bet.setText(bet.toString())
        act_int_mycoin.setText(mycoin.toString())

        //スロットボタンの設定
        val idArray:Array<ImageButton> =
                arrayOf(act_button_image1,act_button_image2,act_button_image3)
        for(i in 0..2){
            slotArray[i] = slotButton(idArray[i])
            slotArray[i]?.id?.setOnClickListener{
                setImage(slotArray[i])
            }
        }
        act_button_back.setOnClickListener{
            val intent: Intent = Intent(this,MainActivity::class.java)
            val pref = PreferenceManager.getDefaultSharedPreferences(this)
            pref.edit().putInt("mycoin", mycoin).commit()
            startActivity(intent)
        }
    }



}
