package com.webmistry.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.webmistry.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private var navigation:Boolean?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
            if (savedInstanceState==null){
                navigation= true
                navigate()
            }else{
                val containKey=savedInstanceState.containsKey(DataKey)
                if (containKey){
                    navigate()
                }
            }
    }

    private  fun navigate(){

        val mainThread=object :Thread(){
            override fun run() {
                super.run()
                try {
                    sleep(1500)
                }catch (e:Exception){
                    e.printStackTrace()
                }finally {
                    startActivity(Intent(this@SplashActivity,MainActivity::class.java))
                    finish()
                }
            }
        }.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (navigation!=true){
            outState.putBoolean(DataKey,navigation!!)
        }
    }

    companion object{
        const val DataKey="DataKey"
    }
}