package com.example.password_genreator.view

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import com.example.password_genreator.R
import com.example.password_genreator.databinding.ActivitySecondBinding


class Second : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivitySecondBinding
    lateinit var dialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener(this)





    }
    private val requestInt = 2

    override fun onClick(p0: View?) {
        if (p0==binding.btnSave){
            if (TextUtils.isEmpty(binding.editTextPassword.text.toString())){
                Toast.makeText(this, "مقدار رمز ساخته شده نمیتواند خالی باشد", Toast.LENGTH_SHORT).show()
            }else if(binding.editTextPassword.text.toString().toInt()>=22||binding.editTextPassword.text.toString().toInt()>223) {
                Toast.makeText(this, "تولید رمز کمتر از رقم 25 تولید میشود", Toast.LENGTH_SHORT).show()

            }else{
                val intent = intent
                intent.putExtra(ConstObject.lenghtPass,binding.editTextPassword.text.toString())
                intent.putExtra(ConstObject.description_password,binding.editTextDescription.text.toString())
                setResult(requestInt,intent)
                getNotificationLogin("پسوردباموفقیت ایجادشد","")
                finish()
            }


        }
    }
    fun getNotificationLogin(titel:String,text:String){
        val notificationBuilder = Notification.Builder(this,ConstObject.ChanelId_AddNotes)
            .setContentTitle(titel)
            .setContentText(text)
            .setSmallIcon(R.mipmap.ic_launcher)

        val getSystemService = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val notifyChannelId = NotificationChannel(ConstObject.ChanelId_AddNotes,"passwordGenerator",NotificationManager.IMPORTANCE_HIGH)
            getSystemService.createNotificationChannel(notifyChannelId)
        }
        getSystemService.notify(0,notificationBuilder.build())



    }


}
