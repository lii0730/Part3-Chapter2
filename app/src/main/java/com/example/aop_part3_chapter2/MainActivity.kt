package com.example.aop_part3_chapter2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var remoteConfig : FirebaseRemoteConfig
    private lateinit var quotes : String
    private var jsonCollection : MutableList<JSONObject> = mutableListOf()

    private val viewPager : ViewPager2 by lazy {
        findViewById(R.id.viewPager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: get RemoteConfig Instance
        initRemoteConfig()

    }

    private fun initRemoteConfig() {
        remoteConfig = Firebase.remoteConfig

        remoteConfig.fetchAndActivate().addOnCompleteListener{
            if(it.isSuccessful){
                //TODO: JSON 형태로 넘어옴 -> 다수의 명언을 받기 위해서
                quotes = remoteConfig.get("quotes").asString()
                viewPager.adapter = MyViewPagerAdapter(parseJsonArray())
            } else {
            }
        }

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
            build()
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
    }

    private fun parseJsonArray() : MutableList<JSONObject>{
        //TODO: 최상위 JSON Object를 가져옴
        val allJsonData = remoteConfig.get("quotes").asString()
        val jObject = JSONObject(allJsonData)

        val jsonArray = jObject.getJSONArray("quotes")

        for(index in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(index)
            jsonCollection.add(obj)
        }

        return jsonCollection
    }
}