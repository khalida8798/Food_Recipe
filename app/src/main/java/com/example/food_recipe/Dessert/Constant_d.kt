package com.example.food_recipe.MainDish

object Constant_d {
    const val DB_VERSION = 1
    const val DB_NAME = "MY_RECORD_DB_d"
    const val Table_name_d = "My_Records_table"
    const val C_ID_D = "ID"
    const val C_NAME_D = "NAME"
    const val C_IMAGE_D = "IMAGE"
    const val C_INGREDIENTS_D = "INGREDIENTS"
    const val C_STEPS_D = "STEPS"
    const val C_ADDED_TIMESTAMP_D = "ADDED_TIMESTAMP"
    const val C_UPDATED_TIMESTAMP_D = "UPDATED_TIMESTAMP"

    const val CREATE_TABLE = "CREATE TABLE $Table_name_d (" +
            C_ID_D + " INTEGER PRIMARY KEY," +
            C_NAME_D + " TEXT," + C_IMAGE_D + " TEXT," +
            C_INGREDIENTS_D + " TEXT," + C_STEPS_D + " TEXT," +
            C_ADDED_TIMESTAMP_D + " TEXT," +
            C_UPDATED_TIMESTAMP_D + " TEXT);"



}


