package com.example.password_genreator.view

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import ir.tapsell.sdk.advertiser.TapsellAdActivity.ZONE_ID
import ir.tapsell.sdk.bannerads.TapsellBannerType
import ir.tapsell.sdk.bannerads.TapsellBannerViewEventListener
import ir.tapsell.sdk.nativeads.TapsellNativeBannerManager
import ir.tapsell.sdk.nativeads.TapsellNativeBannerViewManager


class Second : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivitySecondBinding
    lateinit var dialog: AlertDialog
    private val requestInt = 2
    private var adIds = ""
    private var numberCharacter = false
    private var symbolsCharacter = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFont(this, binding.root)

        val banner = binding.banners
        banner.loadAd(this, ZONE_ID, TapsellBannerType.BANNER_320x50)
        banner.setEventListener(object : TapsellBannerViewEventListener {
            override fun onRequestFilled() {}
            override fun onNoAdAvailable() {}
            override fun onNoNetwork() {}
            override fun onError(message: String) {}
            override fun onHideBannerView() {}
        })


        binding.checkBoxNumber.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                numberCharacter = true
            }

        }
        showfabPromt()
        binding.btnSave.setOnClickListener {
            if (TextUtils.isEmpty(binding.editTextPassword.text.toString())) {
                Toast.makeText(this, "مقدار رمز ساخته شده نمیتواند خالی باشد", Toast.LENGTH_SHORT)
                    .show()
            } else if (binding.editTextPassword.text.toString().toInt() >= 26 ||
                binding.editTextPassword.text.toString().toInt() > 266
            ) {
                Toast.makeText(this, "تولید رمز کمتر از رقم 25 تولید میشود", Toast.LENGTH_SHORT)
                    .show()


            } else {
                val intent = intent
                intent.putExtra(ConstObject.lenghtPass, binding.editTextPassword.text.toString())
                intent.putExtra(
                    ConstObject.description_password,
                    binding.editTextDescription.text.toString()
                )
                intent.putExtra(ConstObject.passwordSymbol.toString(), symbolsCharacter)
                intent.putExtra(ConstObject.numberPassword.toString(), numberCharacter)
                setResult(requestInt, intent)
                finish()


            }
            return@setOnClickListener
        }
        TapsellPlus.initialize(this,
            "nnoqftnholtkopljdlbahqintjiahrjetplcncpoafcheabalnjbabctdimqkakdhspdkp",
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

    }

    private fun showAd(
        adId: Array<String>,
        tapsellNativeBannerViewManager: TapsellNativeBannerViewManager
    ) {
        TapsellNativeBannerManager.bindAd(
            this,
            tapsellNativeBannerViewManager,
            "63cb5efa32592f217376a41c",
            adId.get(0).toString()
        )
    }

    override fun onClick(p0: View?) {
        if (p0 == binding.btnSave) {


        }
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
            })
        }
    }

    private fun descriptionPromt() {
        TapTargetView.showFor(this, TapTarget.forView(
            binding.editTextDescription, "توضیحات پسورد ساخته شده",
            "بعد از مشخص کردن تعداد رمز اینجا میتونید توضیحاتی بنویسید این بخش اختیاری میباشد "
        )
            .tintTarget(false)
            .outerCircleColor(R.color.purple_700)
            .textColor(R.color.white), object : TapTargetView.Listener() {
            override fun onTargetClick(view: TapTargetView?) {
                super.onTargetClick(view)
                createdSavePromt()
            }
        }
        )
    }

    private fun createdSavePromt() {
        TapTargetView.showFor(this, TapTarget.forView(
            binding.btnSave, "ایجاد رمز ",
            "الا میتونید رمز خودتون رو ایجاد کنید پس کلیک کن! "
        )
            .tintTarget(false)
            .outerCircleColor(R.color.purple_700)
            .textColor(R.color.white), object : TapTargetView.Listener() {
            override fun onTargetClick(view: TapTargetView?) {
                super.onTargetClick(view)
            }
        })
        App.preferenceEdit.putBoolean("did", true).apply()
    }

    private fun setFont(context: Context, v: View) {
        try {
            if (v is ViewGroup) {
                var vg = v

                for (i in 0 until vg.childCount) {

                    val child = vg.getChildAt(i)
                    setFont(context, child)
                }
            } else if (v is TextView) {

                v.typeface = Typeface.createFromAsset(assets, "iransans.ttf")
            }
        } catch (e: Exception) {
        }
    }
}
