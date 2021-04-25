package com.example.aop_part3_chapter2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.util.*

class MyViewPagerAdapter(val jsonCollection: MutableList<JSONObject>) :
    RecyclerView.Adapter<MyViewPagerAdapter.ViewHolder>() {


    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val wiseSayingTextView: TextView by lazy {
            itemview.findViewById(R.id.wiseSayingTextView)
        }

        val peopleTextView: TextView by lazy {
            itemview.findViewById(R.id.peopleTextView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.viewpager_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO: remoteConfig로 받은 JSON을 Parsing
        //TODO: list index를 넘어갈때 오류 -> 저장된 명언은 3개인데 ItemCount 는 Integer.maxvlaue
        val value = Random().nextInt(jsonCollection.size)
        holder.wiseSayingTextView.text = jsonCollection.get(value).getString("quote")
        holder.peopleTextView.text = jsonCollection.get(value).getString("people")
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }
}