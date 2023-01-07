package com.example.pomodoro
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*
import kotlin.coroutines.*
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
class MainActivity : AppCompatActivity() {
    lateinit var titleTv:TextView
    lateinit var timerTv:TextView
    lateinit var start:Button
    lateinit var reset:Button
    lateinit var  progressbar:ProgressBar
    var timer:CountDownTimer?=null
    var isTimerStart:Boolean=false
    var StartTimeInMl: Long =25 * 60 *1000
    var resteTemp:Long=StartTimeInMl
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        titleTv = findViewById(R.id.take_pomodoro)
        timerTv = findViewById(R.id.timer)
        start = findViewById(R.id.start_btn)
        reset = findViewById(R.id.reset_btn)
        progressbar = findViewById(R.id.progressBar)
        reset.setOnClickListener{
            resetfun()
        }
        start.setOnClickListener{
            if(!isTimerStart){
                BeginTimer(StartTimeInMl)
                titleTv.text=resources.getText(R.string.keep_going)
            }
        }
    }
    private fun BeginTimer(startTime:Long) {
         timer = object : CountDownTimer(startTime, 1000) {
            override fun onTick(temp: Long) {
                resteTemp=temp
                modifieTempText()
                progressbar.progress=resteTemp.toDouble().div(StartTimeInMl.toDouble()).times(100).toInt()
            }
            override fun onFinish() {
                isTimerStart=false
              
            }

        }.start()
        isTimerStart=true
    }
    private fun modifieTempText(){
        val minute=resteTemp.div(1000).div(60)
        val seconde=resteTemp.div(1000) % 60
        val formatTemp=String.format("%02d:%02d", minute, seconde)
        timerTv.text=formatTemp
    }
    private  fun resetfun(){
        timer?.cancel()
        resteTemp=StartTimeInMl
        modifieTempText()
        titleTv.text=resources.getText(R.string.take_pomodoro)
        isTimerStart=false
        progressbar.progress=100
    }
    val keyOftempRest="tempReste"
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(keyOftempRest,resteTemp)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
      val tempsSauvegarder=  savedInstanceState.getLong(keyOftempRest)
        if (tempsSauvegarder!=StartTimeInMl){
            BeginTimer(tempsSauvegarder)
        }
    }
}



