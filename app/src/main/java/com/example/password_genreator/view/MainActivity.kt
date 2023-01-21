package com.example.password_genreator.view


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.password_genreator.R
import com.example.password_genreator.adapter.MyAdapter
import com.example.password_genreator.app.App
import com.example.password_genreator.database.Data
import com.example.password_genreator.database.DataBaseBuild
import com.example.password_genreator.database.DataDao
import com.example.password_genreator.databinding.ActivityMainBinding
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import ir.tapsell.plus.AdRequestCallback
import ir.tapsell.plus.AdShowListener
import ir.tapsell.plus.TapsellPlus
import ir.tapsell.plus.model.TapsellPlusAdModel
import ir.tapsell.plus.model.TapsellPlusErrorModel
import ir.tapsell.sdk.*
import ir.tapsell.sdk.advertiser.TapsellAdActivity
import ir.tapsell.sdk.advertiser.TapsellAdActivity.ZONE_ID
import ir.tapsell.sdk.bannerads.TapsellBannerType
import ir.tapsell.sdk.bannerads.TapsellBannerViewEventListener
import kotlin.random.Random


class MainActivity : AppCompatActivity(), MyAdapter.GetBuildItemSetView {
    private lateinit var binding: ActivityMainBinding
    private var list = mutableListOf<Data>()
    private lateinit var dataDao: DataDao
    private val requestCodes = 1
    private lateinit var adapter: MyAdapter
    private lateinit var textViewShowPassword: TextView
    private lateinit var datas: Data
    private lateinit var clipboardManager: ClipboardManager
    private lateinit var adIds:String
    private  var avaibleBolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dataDao = DataBaseBuild.buildCreatedObj(this).getDao()


        Tapsell.requestAd(this,
            "63cb813a32592f217376a41f",
            TapsellAdRequestOptions(),
            object : TapsellAdRequestListener() {
                override fun onAdAvailable(adId: String) {
                    adIds = adId
                    avaibleBolean = true
                }
                override fun onError(message: String) {
                    Toast.makeText(this@MainActivity, "$message", Toast.LENGTH_SHORT).show()
                }

            })


        showfabPromt()


        setFonts(this, binding.root)
        getAll()
        adapter = MyAdapter(list, this, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        textViewShowPassword = binding.textviewShow


        binding.floatingActionButton2.setOnClickListener {
            if (avaibleBolean==true) {
                Tapsell.showAd(this,
                    "63cb813a32592f217376a41f",
                    adIds,
                    TapsellShowOptions(),
                    object : TapsellAdShowListener() {
                        override fun onOpened() {

                        }

                        override fun onClosed() {}
                        override fun onError(message: String) {}
                        override fun onRewarded(completed: Boolean) {}
                    })
            }
            val intent = Intent(this, Second::class.java)
            startActivityForResult(intent, requestCodes)
            textViewShowPassword.visibility = View.VISIBLE



        }

        binding.buttonSave.setOnClickListener {
            if (textViewShowPassword.text.equals("")) {
                Toast.makeText(this, "لطفا ابتدا رمزی بسازید", Toast.LENGTH_SHORT).show()
            } else {
                select(datas)
                adapter.notifyDataSetChanged()
                getAll()
                textViewShowPassword.setOnClickListener {
                }
            }

            textViewShowPassword.setOnClickListener {
                if (textViewShowPassword.text.equals("")) {
                    Toast.makeText(this, "ابتدا رمزی را بسازید ", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else {
                    copyText(textViewShowPassword.rootView)
                }
            }
        }
        val banner = binding.banner
        banner.loadAd(this, ZONE_ID, TapsellBannerType.BANNER_320x50	)

        banner.setEventListener(object : TapsellBannerViewEventListener {
            override fun onRequestFilled() {}
            override fun onNoAdAvailable() {}
            override fun onNoNetwork() {}
            override fun onError(message: String) {}
            override fun onHideBannerView() {}
        })
    }




    fun copyText(view: View) {
        val text = binding.textviewShow.text.toString()
        if (text.isNotEmpty()) {
            clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("key", text)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(applicationContext, "Copied", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "No text to be copied", Toast.LENGTH_SHORT).show()
        }
    }

    fun getRandPassword(inputvalidation: Int): String {
        val characterSet = "0123456789abcdefghijklmnopqrstuvwxyzABCEFGHIJKLMNOPQRSTUVWXYZ"
        val random = Random(System.nanoTime())
        val password = StringBuilder()
        for (i in 0 until inputvalidation) {
            val rIndex = random.nextInt(characterSet.length)
            password.append(characterSet[rIndex])
        }

        return password.toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 || resultCode == 2) {

            val password = data?.getStringExtra(ConstObject.lenghtPass)
            if (password == null) {

                return
            } else {
                val show = getRandPassword(password.toString().toInt())

                textViewShowPassword.text = show
                datas = Data(
                    id = null,
                    description = data?.getStringExtra(ConstObject.description_password),
                    passGenerator = show
                )
            }


        }

    }

    fun getAll() {
        list.clear()
        val r = dataDao.addAll()
        list.addAll(r)
    }

    fun select(data: Data) {
        dataDao.insert(data)
    }


    override fun textPassViewCopy(position: Int) {

        var list = list[position].passGenerator
        if (list != null) {

            clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("key", list)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(applicationContext, "Copied", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "No text to be copied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun deleteItem(data: Data) {
        dataDao.delete(data)
    }

    private fun setFonts(context: Context, v: View) {
        try {
            if (v is ViewGroup) {
                val vg = v
                for (i in 0 until vg.childCount) {
                    val child = vg.getChildAt(i)
                    setFonts(context, child) }
            } else if (v is TextView) { v.typeface = Typeface.
            createFromAsset(context.assets, "font/bold_font.ttf") }
        } catch (e: Exception) {

        }



    }




    private fun showfabPromt() {
        if (!App.preferences.getBoolean("did", false)) {

            TapTargetView.showFor(this, TapTarget.forView(
                binding.floatingActionButton2, "ساخت رمز",
                "کلیک کن و اولین رمز خودت رو بساز!"
            )
                .tintTarget(false)
                .outerCircleColor(R.color.purple_700)
                .textColor(R.color.white), object : TapTargetView.Listener() {
                override fun onTargetClick(view: TapTargetView?) {
                    super.onTargetClick(view)

                    showTextViewPromt()
                }
            }
            )
        }
    }

    private fun showTextViewPromt(){
        TapTargetView.showFor(this, TapTarget.forView(binding.textviewShow,"نمایش رمز ساخته شده",
            "بعد از اینکه اولین رمز خودرا ساختید این بخش  براتون نمایان میشود و با یه کلیک ساده روش میتونید اون رو کپی (نحوه مپی زمان استفاده از رمزهای ذخیره شده هم صدق میکنه با یک کلیک اون رو میتونید کپی و استفاده کنید)")
            .tintTarget(false)
            .outerCircleColor(R.color.black)
            .textColor(R.color.white),
            object :TapTargetView.Listener() {
                override fun onTargetClick(view: TapTargetView?) {
                    super.onTargetClick(view)
                    showImagePromt()
                }
            }
        )
    }

    private fun  showImagePromt(){
        TapTargetView.showFor(this, TapTarget.forView(binding.buttonSave,"ذخیره رمز ساخته شده",
            "در این بخش میتونی رمزی که ساختی رو ذخیره کنی پیشنهادم اینه اول رمز خودت رو بسازی بعد اینکه ساخت میتونی با یه کلیک ساده روش اون رو ذخیره کنید")
            .tintTarget(false)
            .outerCircleColor(R.color.black)
            .textColor(R.color.white),
            object :TapTargetView.Listener(){
                override fun onTargetClick(view: TapTargetView?) {
                    super.onTargetClick(view)

                    startActivity(Intent(applicationContext,Second::class.java))
                }
            }
        )

    }


}



