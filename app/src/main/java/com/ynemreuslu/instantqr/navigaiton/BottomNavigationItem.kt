package com.ynemreuslu.instantqr.navigaiton

import androidx.annotation.DrawableRes

data class BottomNavigationItem(
    val route: String,
    val title: String,
    @DrawableRes val iconResId: Int
)
