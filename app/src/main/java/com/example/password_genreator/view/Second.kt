package com.example.password_genreator.view

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import com.example.password_genreator.R
import com.example.password_genreator.app.App
import com.example.password_genreator.databinding.ActivitySecondBinding
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView


class Second : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivitySecondBinding
    lateinit var dialog: AlertDialog
    private val requestInt = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showfabPromt()
        binding.btnSave.setOnClickListener{
            if (TextUtils.isEmpty(binding.editTextPassword.text.toString())){
                Toast.makeText(this, "مقدار رمز ساخته شده نمیتواند خالی باشد", Toast.LENGTH_SHORT).show()
            }else if(binding.editTextPassword.text.toString().toInt()>=26||
                binding.editTextPassword.text.toString().toInt()>266) {
                Toast.makeText(this, "تولید رمز کمتر از رقم 25 تولید میشود", Toast.LENGTH_SHORT).show()


            }else{
                val intent = intent
                intent.putExtra(ConstObject.lenghtPass,binding.editTextPassword.text.toString())
                intent.putExtra(ConstObject.description_password,binding.editTextDescription.text.toString())
                setResult(requestInt,intent)
                getNotificationLogin("پسوردباموفقیت ایجادشد","")
                finish()

            }
            return@setOnClickListener
        }





    }

    override fun onClick(p0: View?) {
        if (p0==binding.btnSave){



        }
    }
    fun getNotificationLogin(titel:String,text:String){
        val notificationBuilder = Notification.Builder(this,ConstObject.ChanelId_AddNotes)
            .setContentTitle(titel)
            .setContentText(text)
            .setSmallIcon(R.drawable.profile_icon)

        val getSystemService = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val notifyChannelId = NotificationChannel(ConstObject.ChanelId_AddNotes,"passwordGenerator",NotificationManager.IMPORTANCE_HIGH)
            getSystemService.createNotificationChannel(notifyChannelId)
        }
        getSystemService.notify(0,notificationBuilder.build())



    }
    private fun showfabPromt() {
        if (!App.preferences.getBoolean("did", false)) {

            TapTargetView.showFor(this, TapTarget.forView(
                binding.editTextPassword, "طول رمز",
                "در اینجا با مشخص کردن عددی بین بازه 1تا25رمز خودتون رو بسازید"
            )
                .tintTarget(false)
                .outerCircleColor(R.color.purple_700)
                .textColor(R.color.white), object : TapTargetView.Listener() {
                override fun onTargetClick(view: TapTargetView?) {
                    super.onTargetClick(view)
                    descriptionPromt()
                }

            }
            )
        }
    }
        private fun descriptionPromt() {

            TapTargetView.showFor(this,TapTarget.forView(binding.editTextDescription,"توضیحات پسورد ساخته شده",
                "بعد از مشخص کردن تعداد رمز اینجا میتونی توضیحاتی بنویسید این بخش رو حتی میتونید پر نکنید ")
                .tintTarget(false)
                .outerCircleColor(R.color.purple_700)
                .textColor(R.color.white)
            ,object  :TapTargetView.Listener(){
                    override fun onTargetClick(view: TapTargetView?) {
                        super.onTargetClick(view)

                        createdSavePromt()
                    }
            }
            )
        }
    private fun createdSavePromt(){
        TapTargetView.showFor(this,TapTarget.forView(binding.btnSave,"ایجاد رمز ",
            "الا میتونی رمز خودتو ایجاد کنی پس کلیک کن ")
            .tintTarget(false)
            .outerCircleColor(R.color.purple_700)
            .textColor(R.color.white)
            ,object  :TapTargetView.Listener(){
                override fun onTargetClick(view: TapTargetView?) {
                    super.onTargetClick(view)

                }
            })
        App.preferenceEdit.putBoolean("did",true).apply()
    }



}
