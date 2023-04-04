package com.coppel.rhconecta.dev.presentation.calculator.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coppel.rhconecta.dev.R
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.Header
import com.coppel.rhconecta.dev.presentation.calculator.dashboard.model.Item
import com.coppel.rhconecta.dev.views.utils.TextUtilities
import com.skydoves.balloon.Balloon

class SavingRecyclerAdapter(var list: List<Item>) : RecyclerView.Adapter<SavingRecyclerAdapter.ViewHolderItem>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        val inflater  = LayoutInflater.from(parent.context)
        return when(viewType){
            0 -> ViewHolderHeader(inflater.inflate(R.layout.item_header, parent, false))
            else -> ViewHolderItem(inflater.inflate(R.layout.item_calculator_dashbord , parent, false))
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        val data = list[position]
        holder.title.text = data.title
        holder.amount.text = data.amount
        data.icon?.let {
            holder.icon.setBackgroundResource(it)
            holder.balloon = TextUtilities.getBallon(holder.view.context,data.label!!)
            holder.icon.setOnClickListener { v ->
                holder.balloon.showAlignBottom(v)
            }
        }?: holder.icon.setImageDrawable(null)
        holder.divider?.let {
            val background = if (isLastItemBetweenSection(position) ) R.color.gray_divider_calculator else android.R.color.white
            it.setBackgroundResource(background)
        }

        if( data is Header) {
           if ( holder is ViewHolderHeader){
               if (data.detail){
                   holder.subTitle.visibility = VISIBLE
                   holder.amount.visibility = VISIBLE
                   holder.amount.text = data.generateAmount()
                   holder.subTitle.text = data.generateSubtitle()
               } else{
                   holder.subTitle.visibility = GONE
                   holder.amount.visibility = GONE
               }
           }
        }
    }
     private fun isLastItemBetweenSection(index : Int) : Boolean {
         val last = list.size - 1
         return if(last == index)
             true
         else {
             list[index+1] is Header
         }
     }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] is Header){ 0 } else 1
    }
    class ViewHolderHeader(view : View) : ViewHolderItem(view) {
        val subTitle : TextView = view.findViewById(R.id.textView27)
    }

    open class ViewHolderItem (val view :View) : RecyclerView.ViewHolder(view) {
        var title : TextView = view.findViewById(R.id.textView28)
        var amount : TextView = view.findViewById(R.id.textView29)
        val icon : ImageView = view.findViewById(R.id.imageView6)
        val divider : View? = view.findViewById(R.id.divider)
        lateinit var balloon : Balloon
    }
}
