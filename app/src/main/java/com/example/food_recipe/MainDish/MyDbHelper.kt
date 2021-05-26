package com.example.food_recipe.MainDish

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.food_recipe.MainDish.Constant
import com.example.food_recipe.MainDish.Constant.Table_name
import com.example.food_recipe.MainDish.maindishrecord

class MyDbHelper(context: Context?): SQLiteOpenHelper(
    context,
    Constant.DB_NAME,
    null,
    Constant.DB_VERSION

) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Constant.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS" + Constant.Table_name)
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
        values.put(Constant.C_NAME, name)
        values.put(Constant.C_IMAGE, image)
        values.put(Constant.C_INGREDIENTS, ingredients)
        values.put(Constant.C_STEPS, step)
        values.put(Constant.C_ADDED_TIMESTAMP, addedtime)
        values.put(Constant.C_UPDATED_TIMESTAMP, updatedtime)


        val id = db.insert(Constant.Table_name, null, values)
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


        values.put(Constant.C_NAME, name)
        values.put(Constant.C_IMAGE, image)
        values.put(Constant.C_INGREDIENTS, ingredients)
        values.put(Constant.C_STEPS, step)
        return db.update(
            (Constant.Table_name),
            values,
            "${Constant.C_ID}=?",
            arrayOf(id)
        ).toLong()

    }


    fun getAllData(orderBy: String): ArrayList<maindishrecord> {
        val maindishlist = ArrayList<maindishrecord>()
        val selectQuery = "SELECT * FROM  ${Table_name} ORDER BY $orderBy"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val maindishrecord = maindishrecord(
                    "" + cursor.getInt(cursor.getColumnIndex(Constant.C_ID)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant.C_NAME)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant.C_IMAGE)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant.C_INGREDIENTS)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant.C_STEPS)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant.C_ADDED_TIMESTAMP)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant.C_UPDATED_TIMESTAMP))


                )
                maindishlist.add(maindishrecord)
            } while (cursor.moveToNext())
        }
        db.close()
        return maindishlist
    }

    fun searchRecords(query: String): ArrayList<maindishrecord> {
        val maindishlist = ArrayList<maindishrecord>()
        val selectQuery =
            "SELECT * FROM  ${Constant.Table_name} WHERE ${Constant.C_NAME} LIKE '$query%'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val maindishrecord = maindishrecord(
                    "" + cursor.getInt(cursor.getColumnIndex(Constant.C_ID)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant.C_NAME)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant.C_IMAGE)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant.C_INGREDIENTS)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant.C_STEPS)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant.C_ADDED_TIMESTAMP)),
                    "" + cursor.getString(cursor.getColumnIndex(Constant.C_UPDATED_TIMESTAMP)),
                )
                maindishlist.add(maindishrecord)
            } while (cursor.moveToNext())
        }
        db.close()
        return maindishlist
    }
    fun recordCount():Int{
        val countQuery = "SELECT * FROM ${Constant.Table_name}"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)
        val count = cursor.count
        cursor.close()
        return count
    }

    fun deleteRecord(id: String){
        val db = writableDatabase
        db.delete(
            Constant.Table_name,
            "${Constant.C_ID} =?",
            arrayOf(id)
        )
        db.close()

}
    fun deleteAllRecords() {
        val db = writableDatabase
        db.execSQL("DELETE FROM ${Constant.Table_name}")
        db.close()
}
}