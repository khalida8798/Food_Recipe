package com.example.food_recipe.MainDish

object Constant {
    const val DB_VERSION = 1
    const val DB_NAME = "MY_RECORD_DB"
    const val Table_name = "My_Records_table"
    const val C_ID = "ID"
    const val C_NAME = "NAME"
    const val C_IMAGE = "IMAGE"
    const val C_INGREDIENTS = "INGREDIENTS"
    const val C_STEPS = "STEPS"
    const val C_ADDED_TIMESTAMP = "ADDED_TIMESTAMP"
    const val C_UPDATED_TIMESTAMP = "UPDATED_TIMESTAMP"

   const val CREATE_TABLE = "CREATE TABLE $Table_name (" +
            C_ID + " INTEGER PRIMARY KEY," +
            C_NAME + " TEXT," + C_IMAGE + " TEXT," +
            C_INGREDIENTS + " TEXT," + C_STEPS + " TEXT," +
            C_ADDED_TIMESTAMP + " TEXT," +
            C_UPDATED_TIMESTAMP + " TEXT);"



}


