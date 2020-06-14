package com.internshala.foodapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FoodEntity::class], version = 1)
abstract class BookDatabase: RoomDatabase() {

    abstract fun bookDao(): FoodDao

}