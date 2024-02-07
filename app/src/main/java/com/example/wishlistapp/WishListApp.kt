package com.example.wishlistapp

import android.app.Application
import com.example.wishlistapp.data.Graph


//you need to change the manifest and add android:name=".WishListApp" to it
class WishListApp: Application()
{
    override fun onCreate(){
        super.onCreate()
        //this creates the database on initlization
        Graph.provide(this)
    }
}