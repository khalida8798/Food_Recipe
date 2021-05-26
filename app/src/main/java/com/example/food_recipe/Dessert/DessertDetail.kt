package com.example.food_recipe.Dessert

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food_recipe.MainDish.Constant_d
import com.example.food_recipe.R
import kotlinx.android.synthetic.main.activity_main_dish_detail.*
import kotlinx.android.synthetic.main.activity_main_dish_detail.image_maindish
import kotlinx.android.synthetic.main.activity_main_dish_detail.maindish_text
import java.util.*

class DessertDetail : AppCompatActivity() {

    private var dbHelper: MyDbHelper_d?= null
    private var recordId: String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dish_detail)

        var actionBar = supportActionBar

        // showing the back button in action bar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        dbHelper = MyDbHelper_d (this)

        val intent = intent
        recordId = intent.getStringExtra("RECORD_ID_D")

        showRecordDetails()

    }

    @SuppressLint("Recycle")
    private fun showRecordDetails() {
        val selectQuery = "SELECT * FROM " + Constant_d.Table_name_d + " WHERE " + Constant_d.C_ID_D + " =\""+recordId+"\""
        val db = dbHelper!!.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()){
            do{
                val id = ""+cursor.getInt(cursor.getColumnIndex(Constant_d.C_ID_D))
                val name = ""+cursor.getString(cursor.getColumnIndex(Constant_d.C_NAME_D))
                val image = ""+cursor.getString(cursor.getColumnIndex(Constant_d.C_IMAGE_D))
                val ingredients = ""+cursor.getString(cursor.getColumnIndex(Constant_d.C_INGREDIENTS_D))
                val steps = ""+cursor.getString(cursor.getColumnIndex(Constant_d.C_STEPS_D))
                val addedtime = ""+cursor.getString(cursor.getColumnIndex(Constant_d.C_ADDED_TIMESTAMP_D))
                val updatedtime = ""+cursor.getString(cursor.getColumnIndex(Constant_d.C_UPDATED_TIMESTAMP_D))

                val calendar1 = Calendar.getInstance(Locale.getDefault())
                calendar1.timeInMillis = addedtime.toLong()
                val timeadded = android.text.format.DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar1)

                val calendar2 = Calendar.getInstance(Locale.getDefault())
                calendar2.timeInMillis = updatedtime.toLong()
                val timeupdated = android.text.format.DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar2)

                maindish_text.text = name
                ingredient2.text = ingredients
                step2.text = steps
                image_maindish.setImageURI(Uri.parse(image))

            } while (cursor.moveToNext())

        }
        db.close()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}