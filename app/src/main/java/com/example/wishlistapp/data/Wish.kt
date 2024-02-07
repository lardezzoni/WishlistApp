package com.example.wishlistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Table for Room DAO
@Entity(tableName = "wish-table")
data class Wish(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "wish-title")
    val title: String = "",
    @ColumnInfo(name = "wish-desc")
    val description: String = ""
)

object DummyWish{
    val wishList = listOf(
        Wish(title = "Google Watch", description = "Android watch wish"),
        Wish(title = "Google DOM", description = "SLSLSL watch wish"),
                Wish(title = "Google MORE", description = "SLSLSLSAASSA watch wish")

    )
}