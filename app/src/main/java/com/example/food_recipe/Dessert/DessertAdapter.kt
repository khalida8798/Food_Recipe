package com.example.food_recipe.Dessert

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
import com.example.food_recipe.MainDish.*
import kotlin.collections.ArrayList

class dessertAdapter() : RecyclerView.Adapter<dessertAdapter.ViewHolder>() {

    private var context: Context? = null
    private var dessertlist: ArrayList<dessertrecord>? = null

    lateinit var dbHelper: MyDbHelper_d

    constructor(context: Context?, dessertlist: ArrayList<dessertrecord>) : this() {
        this.context = context
        this.dessertlist = dessertlist

        dbHelper = MyDbHelper_d(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.dessert_card, parent, false))
    }



    override fun getItemCount(): Int {
        return  dessertlist!!.size
    }

    override fun onBindViewHolder(holder: dessertAdapter.ViewHolder, position: Int) {
        val model = dessertlist!!.get(position)
        val id = model.id
        val name = model.name
        val image = model.image
        val ingredients = model.ingredients
        val steps = model.steps
        val addedTime = model.addedTime
        val updatedTime = model.updatedTime

        holder.dessert_text.text = name
        holder.image_dessert.setImageURI(Uri.parse(image))


        holder.itemView.setOnClickListener {
            val intent = Intent(context, DessertDetail::class.java)
            intent.putExtra("RECORD_ID_D", id)
            context!!.startActivity(intent)
        }

        holder.morebutton_d.setOnClickListener {
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

    private fun showMoreOptions(
        position: Int,
        id: String,
        name: String,
        image: String,
        ingredients: String,
        steps: String,
        addedTime: String,
        updatedTime: String)
    {
        val options = arrayOf("Edit", "Delete")
        val dialog : AlertDialog.Builder = AlertDialog.Builder(context)
        dialog.setItems(options) {dialog, which ->

            if (which==0) {
                val intent = Intent(context, AddUpdateRecordActivity_d::class.java)
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

                (context as Dessert)!!.onResume()

            } }
        dialog.show()
    }


    inner class ViewHolder (itemView : View): RecyclerView.ViewHolder(itemView){

        var image_dessert:ImageView = itemView.findViewById(R.id.image_dessert)
        var dessert_text:TextView = itemView.findViewById(R.id.dessert_text)
        var morebutton_d:ImageButton = itemView.findViewById(R.id.morebutton_d)
    }
}





