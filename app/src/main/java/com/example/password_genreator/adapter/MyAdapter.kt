package com.example.password_genreator.adapter

import android.content.Context
import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.password_genreator.databinding.ResFileBinding


class MyAdapter(var item: MutableList<com.example.password_genreator.database.Data>, val context: Context): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    inner class MyViewHolder(val binding:ResFileBinding): RecyclerView.ViewHolder(binding.root){

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder {
       return MyViewHolder(ResFileBinding.inflate(LayoutInflater.from(context),parent,false))
    }


    override fun getItemCount(): Int {
       return item.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }
}
