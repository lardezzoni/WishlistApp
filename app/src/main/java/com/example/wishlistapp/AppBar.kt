package com.example.wishlistapp

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarView(
    title: String,
    onBackNavClicker: () -> Unit = {}
){
    val navigationIcon : (@Composable ()-> Unit)? =
        //check if title contains something to actually show the back arrow
        if(!title.contains("WishList")){
            {
                IconButton(onClick = { onBackNavClicker() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = Color.Black,
                        contentDescription = null
                    )
                }
            }
        }else{
            null
        }

    //R.color.white is inside /res/values/colors.xml
    TopAppBar(
        title = { Text(text = title, color= colorResource(id = R.color.black),
        modifier = Modifier
            .padding(start = 4.dp)
            .heightIn(max = 24.dp)) },
        elevation = 3.dp,
        backgroundColor = colorResource(id = R.color.app_bar_color),
        navigationIcon = navigationIcon
        )
}