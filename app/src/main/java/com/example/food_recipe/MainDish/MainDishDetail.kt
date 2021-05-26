package com.example.food_recipe.MainDish

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food_recipe.R
import kotlinx.android.synthetic.main.activity_main_dish_detail.*
import kotlinx.android.synthetic.main.activity_main_dish_detail.image_maindish
import kotlinx.android.synthetic.main.activity_main_dish_detail.maindish_text
import java.util.*

class MainDishDetail : AppCompatActivity() {

  private var dbHelper: MyDbHelper?= null
    private var recordId: String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dish_detail)

        var actionBar = supportActionBar

        // showing the back button in action bar
        actionBar?.setDisplayHomeAsUpEnabled(true)

      dbHelper = MyDbHelper (this)

        val intent = intent
        recordId = intent.getStringExtra("RECORD_ID")

        showRecordDetails()

    }

    @SuppressLint("Recycle")
    private fun showRecordDetails() {
        val selectQuery = "SELECT * FROM " + Constant.Table_name + " WHERE " + Constant.C_ID + " =\""+recordId+"\""
        val db = dbHelper!!.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()){
            do{
                val id = ""+cursor.getInt(cursor.getColumnIndex(Constant.C_ID))
                val name = ""+cursor.getString(cursor.getColumnIndex(Constant.C_NAME))
                val image = ""+cursor.getString(cursor.getColumnIndex(Constant.C_IMAGE))
                val ingredients = ""+cursor.getString(cursor.getColumnIndex(Constant.C_INGREDIENTS))
                val steps = ""+cursor.getString(cursor.getColumnIndex(Constant.C_STEPS))
                val addedtime = ""+cursor.getString(cursor.getColumnIndex(Constant.C_ADDED_TIMESTAMP))
                val updatedtime = ""+cursor.getString(cursor.getColumnIndex(Constant.C_UPDATED_TIMESTAMP))

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