package com.example.food_recipe.Dessert

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.food_recipe.MainDish.Constant
import com.example.food_recipe.MainDish.Constant.Table_name
import com.example.food_recipe.MainDish.Constant_d
import com.example.food_recipe.MainDish.Constant_d.Table_name_d
import com.example.food_recipe.MainDish.maindishrecord

class MyDbHelper_d(context: Context?): SQLiteOpenHelper(
    context,
    Constant_d.DB_NAME,
    null,
    Constant_d.DB_VERSION

) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Constant_d.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS" + Constant_d.Table_name_d)
        onCreate(db)
    }

    fun insertRecord(
        name: String?,
        image: String?,
        ingredients: String?,
        step: String?,
        addedtime: String?,
        updatedtime: String?

    ): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(Constant_d.C_NAME_D, name)
        values.put(Constant_d.C_IMAGE_D, image)
        values.put(Constant_d.C_INGREDIENTS_D, ingredients)
        values.put(Constant_d.C_STEPS_D, step)
        values.put(Constant_d.C_ADDED_TIMESTAMP_D, addedtime)
        values.put(Constant_d.C_UPDATED_TIMESTAMP_D, updatedtime)


        val id = db.insert(Constant_d.Table_name_d, null, values)
        db.close()
        return id

    }

    fun updateRecord(
        id: String,
        name: String?,
        image: String?,
        ingredients: String?,
        step: String?,
        addedtime: String?,
        updatedtime: String?
    ): Long {
        val db = this.writableDatabase
        val values = ContentValues()


        values.put(Constant_d.C_NAME_D, name)
        values.put(Constant_d.C_IMAGE_D, image)
        values.put(Constant_d.C_INGREDIENTS_D, ingredients)
        values.put(Constant_d.C_STEPS_D, step)
        return db.update(
            (Constant_d.Table_name_d),
            values,
            "${Constant_d.C_ID_D}=?",
            arrayOf(id)
        ).toLong()

    }


    fun getAllData(orderBy: String): ArrayList<dessertrecord> {
        val dessertlist = ArrayList<dessertrecord>()
        val selectQuery = "SELECT * FROM  ${Table_name_d} ORDER BY $orderBy"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val dessertrecord = dessertrecord(
                    "" + cursor.getInt(cursor.getColumnIndex(Constant_d.C_ID_D)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant_d.C_NAME_D)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant_d.C_IMAGE_D)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant_d.C_INGREDIENTS_D)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant_d.C_STEPS_D)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant_d.C_ADDED_TIMESTAMP_D)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant_d.C_UPDATED_TIMESTAMP_D))


                )
                dessertlist.add(dessertrecord)
            } while (cursor.moveToNext())
        }
        db.close()
        return dessertlist
    }

    fun searchRecords(query: String): ArrayList<dessertrecord> {
        val dessertlist = ArrayList<dessertrecord>()
        val selectQuery =
            "SELECT * FROM  ${Constant_d.Table_name_d} WHERE ${Constant_d.C_NAME_D} LIKE '$query%'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val dessertrecord = dessertrecord(
                    "" + cursor.getInt(cursor.getColumnIndex(Constant_d.C_ID_D)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant_d.C_NAME_D)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant_d.C_IMAGE_D)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant_d.C_INGREDIENTS_D)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant_d.C_STEPS_D)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant_d.C_ADDED_TIMESTAMP_D)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant_d.C_UPDATED_TIMESTAMP_D)),
                )
                dessertlist.add(dessertrecord)
            } while (cursor.moveToNext())
        }
        db.close()
        return dessertlist
    }
    fun recordCount():Int{
        val countQuery = "SELECT * FROM ${Constant_d.Table_name_d}"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)
        val count = cursor.count
        cursor.close()
        return count
    }

    fun deleteRecord(id: String){
        val db = writableDatabase
        db.delete(
            Constant_d.Table_name_d,
            "${Constant_d.C_ID_D} =?",
            arrayOf(id)
        )
        db.close()

    }

}