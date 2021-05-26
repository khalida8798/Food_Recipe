package com.example.food_recipe.MainDish

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food_recipe.*
import kotlin.collections.ArrayList

class maindishAdapter() : RecyclerView.Adapter<maindishAdapter.ViewHolder>() {

    private var context: Context? = null
    private var maindishlist: ArrayList<maindishrecord>?=null

    lateinit var dbHelper: MyDbHelper
    constructor(context: Context?, maindishlist: ArrayList<maindishrecord>?) : this() {
        this.context = context
        this.maindishlist = maindishlist

        dbHelper = MyDbHelper(context)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.maindish_card, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return  maindishlist!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = maindishlist!!.get(position)
        val id = model.id
        val name = model.name
        val image = model.image
        val ingredients = model.ingredients
        val steps = model.steps
        val addedTime = model.addedTime
        val updatedTime = model.updatedTime

        holder.maindish_text.text = name
        holder.image_maindish.setImageURI(Uri.parse(image))


        holder.itemView.setOnClickListener {
          val intent = Intent(context, MainDishDetail::class.java)
            intent.putExtra("RECORD_ID", id)
            context!!.startActivity(intent)
        }

        holder.morebutton.setOnClickListener{
            showMoreOptions(
                    position,
                    id,
                    name,
                    image,
                    ingredients,
                    steps,
                    addedTime,
                    updatedTime
                    )

        }

    }


    private fun showMoreOptions(position: Int, id:String, name:String, image:String, ingredients:String, steps:String, addedTime:String, updatedTime:String
    ) {
        val options = arrayOf("Edit", "Delete")
        val dialog : AlertDialog.Builder = AlertDialog.Builder(context)
        dialog.setItems(options) {dialog, which ->

            if (which==0) {
                val intent = Intent(context, AddUpdateRecordActivity::class.java)
                intent.putExtra("ID", id)
                intent.putExtra("NAME", name)
                intent.putExtra("INGREDIENTS", ingredients)
                intent.putExtra("IMAGE", image)
                intent.putExtra("STEPS", steps)
                intent.putExtra("TIMEADDED", addedTime)
                intent.putExtra("UPDATEDTIME", updatedTime)
                intent.putExtra("isEditMode",true)
                context!!.startActivity(intent)

            } else{
            dbHelper.deleteRecord(id)

                (context as Maindish)!!.onResume()

            } }
    dialog.show()
    }

 inner class ViewHolder (itemView : View): RecyclerView.ViewHolder(itemView){

    var image_maindish:ImageView = itemView.findViewById(R.id.image_maindish)
    var maindish_text:TextView = itemView.findViewById(R.id.maindish_text)
    var morebutton:ImageButton = itemView.findViewById(R.id.morebutton)
}
}





