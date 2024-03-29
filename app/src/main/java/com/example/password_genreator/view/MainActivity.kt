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
import ir.tapsell.sdk.*
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
    private lateinit var adIds: String
    private var avaibleBolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setFonts(this, binding.root)
        dataDao = DataBaseBuild.buildCreatedObj(this).getDao()


        Tapsell.requestAd(this,
            "63cb813a32592f217376a41f",
            TapsellAdRequestOptions(),
            object : TapsellAdRequestListener() {
                override fun onAdAvailable(adId: String) {
                    adIds = adId
                    avaibleBolean = true
                }

                override fun onError(message: String) {} })

        Tapsell.requestAd(this,
            "63cb7b88e7c8497f1bd70d73",
            TapsellAdRequestOptions(),
            object : TapsellAdRequestListener() {
                override fun onAdAvailable(adId: String) {
                    adIds = adId
                    Log.d("PRerfdfdsfsfs", adId)

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

            }
        }

        textViewShowPassword.setOnClickListener {
            if (textViewShowPassword.text.equals("")) {
                Toast.makeText(this, "ابتدا رمزی را بسازید ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                copyText(binding.textviewShow.rootView)
            }
        }


        val banner = binding.banner
        banner.loadAd(this, ZONE_ID, TapsellBannerType.BANNER_320x50)

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
            Toast.makeText(this, "رمز کپی شد", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "رمزی برای کپی وجود ندارد", Toast.LENGTH_SHORT).show()
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 || resultCode == 2) {

            val password = data?.getStringExtra(ConstObject.lenghtPass)
            val symbolCharacter = data?.getBooleanExtra(ConstObject.passwordSymbol.toString(),false)
            val numberCharacter = data?.getBooleanExtra(ConstObject.numberPassword.toString(),false)
            if (password == null) {

                return
            } else {
                val show = getRandPassword(password.toString().toInt(),symbolCharacter.toString().toBoolean(),numberCharacter.toString().toBoolean())
                Log.d("rr",ConstObject.passwordSymbol.toString())

                textViewShowPassword.text = show
                datas = Data(
                    id = null,
                    description = data?.getStringExtra(ConstObject.description_password),
                    passGenerator = show
                )
            }


        }

    }
    fun getRandPassword(inputvalidation: Int , symbolPassword:Boolean , numberCharacterPassword:Boolean): String {

        var  characterSet = "abcdefghijklmnopqrstuvwxyz"

        if (numberCharacterPassword){
            characterSet = "ABCEFGHIJKLMNOPQRSTUVWXYZ!/?%&#@&abcdefghijklmnopqrstuvwxyz0123456789"

        }





        val random = Random(System.nanoTime())
        val password = StringBuilder()
        for (i in 0 until inputvalidation) {
            val rIndex = random.nextInt(characterSet.length)
            password.append(characterSet[rIndex])
        }

        return password.toString()
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
            Toast.makeText(this, "رمز کپی شد", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "رمزی برای کپی وجود ندارد", Toast.LENGTH_SHORT).show()
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
                    setFonts(context, child)
                }
            } else if (v is TextView) {
                v.typeface = Typeface.createFromAsset(context.assets, "font/iransans.ttf")
            }
        } catch (e: Exception) {

        }


    }


    private fun showfabPromt() {
        if (!App.preferences.getBoolean("did", false)) {

            TapTargetView.showFor(this, TapTarget.forView(
                binding.floatingActionButton2, "ساخت رمز",
                "کلیک کنید و اولین رمزتون رو بسازید!"
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

    private fun showTextViewPromt() {
        TapTargetView.showFor(this, TapTarget.forView(
            binding.textviewShow, "نمایش رمز ساخته شده",
            "بعد از اینکه اولین رمز خودرا ساختید این بخش  براتون نمایان میشود و با یه کلیک ساده روش میتونید اون رو کپی (نحوه کپی زمان استفاده از رمزهای ذخیره شده هم صدق میکنه با یک کلیک اون رو میتونید کپی و استفاده کنید)"
        )
            .tintTarget(false)
            .outerCircleColor(R.color.black)
            .textColor(R.color.white),
            object : TapTargetView.Listener() {
                override fun onTargetClick(view: TapTargetView?) {
                    super.onTargetClick(view)
                    showImagePromt()
                }
            }
        )
    }

    private fun showImagePromt() {
        TapTargetView.showFor(this, TapTarget.forView(
            binding.buttonSave, "ذخیره رمز ساخته شده",
            "در این بخش میتونی رمزی که ساختی رو ذخیره کنی پیشنهادم اینه اول رمز خودت رو بسازی بعد اینکه ساخت میتونی با یه کلیک ساده روش اون رو ذخیره کنید"
        )
            .tintTarget(false)
            .outerCircleColor(R.color.black)
            .textColor(R.color.white),
            object : TapTargetView.Listener() {
                override fun onTargetClick(view: TapTargetView?) {
                    super.onTargetClick(view)

                    val intent = Intent(this@MainActivity, Second::class.java)
                    startActivityForResult(intent, requestCodes)
                    textViewShowPassword.visibility = View.VISIBLE
                }
            }
        )

    }

}



