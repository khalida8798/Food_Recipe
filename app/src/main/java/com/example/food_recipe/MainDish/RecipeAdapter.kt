package com.example.food_recipe.MainDish

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.food_recipe.R
import com.example.food_recipe.modal.itemmodal
import kotlinx.android.synthetic.main.recipetypes_card.view.*
import java.util.*
import kotlin.collections.ArrayList

class RecipeAdapter(
   var clickListener: ClickListener
): RecyclerView.Adapter<RecipeAdapter.ViewHolder>(), Filterable{

    var itemmodalList = ArrayList<itemmodal>();
    var itemmodalListFilter = ArrayList<itemmodal>();

    fun setData(itemmodalList: ArrayList <itemmodal>){
        this.itemmodalList = itemmodalList
        this.itemmodalListFilter = itemmodalList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recipetypes_card,parent,false)
        return ViewHolder(v)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemmodal = itemmodalList[position]

        holder.title.text = itemmodal.title
        holder.image.setImageResource(itemmodal.imageSrc)

        holder.itemView.setOnClickListener{
          clickListener.clickeditem(itemmodal)
        }
    }



    override fun getItemCount(): Int {
        return itemmodalList.size
    }

    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){

        val title = itemView.card_text
        val image = itemView.image_card
    }



    interface ClickListener{
        fun clickeditem(itemmodal: itemmodal)
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {

                val filterResults = FilterResults()
                if (charSequence == null || charSequence.isEmpty()) {

                    filterResults.count = itemmodalListFilter.size
                    filterResults.values = itemmodalListFilter;

                } else {

                    var searchChr: String = charSequence.toString().lowercase(Locale.getDefault());
                    val itemmodals = ArrayList<itemmodal>()
                    for (items in itemmodalListFilter) {
                        if (items.title.toLowerCase().contains(searchChr)) {
                            itemmodals.add(items)
                        }

                    }


                    filterResults.count = itemmodals.size
                    filterResults.values = itemmodals

                }
                return filterResults;
            }

                override fun publishResults(p0: CharSequence?, p1: FilterResults?) {

                    itemmodalList = p1!!.values as ArrayList<itemmodal>
                    notifyDataSetChanged()



                }


            }
        }
}
