package com.example.password_genreator.adapter

import android.content.Context
import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.password_genreator.databinding.ResFileBinding


class MyAdapter(var item: MutableList<com.example.password_genreator.database.Data>, val context: Context,val listener:GetBuildItemSetView): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    inner class MyViewHolder(val binding:ResFileBinding): RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        init {
            binding.passwordGeneratorText.setOnClickListener(this)
            binding.deleteBtn.setOnClickListener(this)


        }

        override fun onClick(p0: View?) {

            if (p0 == binding.passwordGeneratorText) {

                listener.textPassViewCopy(position)

            }
            if (p0==binding.deleteBtn){
                val list = item[adapterPosition]
                item.removeAt(position)
                notifyDataSetChanged()
                notifyItemRangeChanged(position,itemCount)

               listener.deleteItem(list)
            }
        }
    }
        interface GetBuildItemSetView{
            fun textPassViewCopy(position: Int)
            fun deleteItem(data: com.example.password_genreator.database.Data)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder {
       return MyViewHolder(ResFileBinding.inflate(LayoutInflater.from(context),parent,false))
    }


    override fun getItemCount(): Int {
       return item.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.descriptionText.text = item[position].description
        holder.binding.passwordGeneratorText.text = item[position].passGenerator

    }
}
