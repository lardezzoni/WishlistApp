package com.example.wishlistapp.data

import android.content.Context
import androidx.room.Room


//This is example of Dependency Injection in kotlin
//This is an example of a Singleton
//There is only one Graph in the app

object Graph {

    //lateinit is useful when we are doing dependency injection
    lateinit var database: WishDatabase

    //by lazy makes sure that this variable is only initialized when it is needed
    //it will be loaded when we are going to access not on the start which could delay the performance
    //of the app
    //the by lazy is safe in a thread safe form
    val wishRepository by lazy{
        WishRepository(wishDAO = database.wishDao())
    }

    //this will create the database when we call the provide function
    fun provide(context: Context){
        database = Room.databaseBuilder(context, klass = WishDatabase::class.java, "wishlist.db").build()
    }
}