package com.example.password_genreator.view

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.password_genreator.R
import com.example.password_genreator.app.App
import com.example.password_genreator.databinding.ActivitySecondBinding
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import ir.tapsell.plus.*
import ir.tapsell.plus.model.AdNetworkError
import ir.tapsell.plus.model.AdNetworks
import ir.tapsell.sdk.*
import ir.tapsell.sdk.advertiser.TapsellAdActivity.AD_ID
import ir.tapsell.sdk.advertiser.TapsellAdActivity.ZONE_ID
import ir.tapsell.sdk.nativeads.TapsellNativeBannerManager
import ir.tapsell.sdk.nativeads.TapsellNativeBannerViewManager


class Second : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivitySecondBinding
    lateinit var dialog: AlertDialog
    private val requestInt = 2
    private var adIds =""
    private var onAdAvailable:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //آنی



        Tapsell.requestAd(this,
            "63cb7b88e7c8497f1bd70d73",
            TapsellAdRequestOptions(),
            object : TapsellAdRequestListener() {
                override fun onAdAvailable(adId: String) {
                    adIds = adId
                }
                override fun onError(message: String) {
                    Toast.makeText(this@Second, "$message", Toast.LENGTH_SHORT).show()
                }
            })

//hamsan tabglig

        var adContainer: ViewGroup = findViewById(R.id.adContainer)
        var tapsellNativeBannerViewManager = TapsellNativeBannerManager.Builder()
            .setParentView(adContainer)
            .setContentViewTemplate(R.layout.layout_banner_secons)
//            .setAppInstallationViewTemplate(ir.tapsell.sdk.R.layout.)
            .inflateTemplate(this)
        TapsellNativeBannerManager.getAd(this, "63cb5efa32592f217376a41c",
            object : ir.tapsell.sdk.AdRequestCallback {
                override fun onResponse(adId: Array<String>) {
                    //ad is ready to show
                    showAd(adId,tapsellNativeBannerViewManager)
                }

                override fun onFailed(message: String) {}
            })



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
//                getNotificationLogin("پسوردباموفقیت ایجادشد","")

                Tapsell.showAd(this,
                    "63cb7b88e7c8497f1bd70d73",
                    adIds,
                    TapsellShowOptions(),
                    object : TapsellAdShowListener() {
                        override fun onOpened() {
                        }
                        override fun onClosed() {}
                        override fun onError(message: String) {}
                        override fun onRewarded(completed: Boolean) {}
                    })



                finish()

            }
            return@setOnClickListener
        }
        TapsellPlus.initialize(this,"nnoqftnholtkopljdlbahqintjiahrjetplcncpoafcheabalnjbabctdimqkakdhspdkp",
            object : TapsellPlusInitListener {
                override fun onInitializeSuccess(adNetworks: AdNetworks) {
                    Log.d("onInitializeSuccess", adNetworks.name)
                }

                override fun onInitializeFailed(
                    adNetworks: AdNetworks,
                    adNetworkError: AdNetworkError
                ) {
                    Log.e(
                        "onInitializeFailed",
                        "ad network: " + adNetworks.name + ", error: " + adNetworkError.errorMessage
                    )
                }
            })






//        val banner = binding.banners
//        banner.loadAd(this, "63c92c0cdf906e0b23c6fee1", TapsellBannerType.BANNER_320x100)
//
//        banner.setEventListener(object : TapsellBannerViewEventListener {
//            override fun onRequestFilled() {}
//            override fun onNoAdAvailable() {}
//            override fun onNoNetwork() {}
//            override fun onError(message: String) {}
//            override fun onHideBannerView() {}
//        })

    }

    private fun showAd(adId: Array<String>,tapsellNativeBannerViewManager: TapsellNativeBannerViewManager) {
        TapsellNativeBannerManager.bindAd(
            this,
            tapsellNativeBannerViewManager,
            "63cb5efa32592f217376a41c",
            adId.get(0).toString()
        )
    }


    override fun onClick(p0: View?) {
        if (p0==binding.btnSave){



        }
    }
//    fun getNotificationLogin(titel:String,text:String){
//        val notificationBuilder = Notification.Builder(this,ConstObject.ChanelId_AddNotes)
//            .setContentTitle(titel)
//            .setContentText(text)
//            .setSmallIcon(R.drawable.profile_icon)
//
//        val getSystemService = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
//            val notifyChannelId = NotificationChannel(ConstObject.ChanelId_AddNotes,"passwordGenerator",NotificationManager.IMPORTANCE_HIGH)
//            getSystemService.createNotificationChannel(notifyChannelId)
//        }
//        getSystemService.notify(0,notificationBuilder.build())




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
