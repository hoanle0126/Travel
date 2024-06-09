package com.example.travel.data.model

import androidx.annotation.DrawableRes
import com.example.travel.R

data class NavigationModel(
    val route: String,
    val name: String,
    @DrawableRes val icon:Int,
    @DrawableRes val iconBold:Int = 0,
)

class ListNavigationModel(){
    fun getAll():List<NavigationModel>{
        return listOf(
            NavigationModel(
                route = "home",
                name = "Khám phá",
                icon = R.drawable.icon_outlined_home,
                iconBold = R.drawable.icon_bold_home
            ),
            NavigationModel(
                route = "search",
                name = "Tìm kiếm",
                icon = R.drawable.icon_outlined_search,
                iconBold = R.drawable.icon_bold_search
            ),
            NavigationModel(
                route = "to-do",
                name = "Lập kế hoạch",
                icon = R.drawable.icon_outlined_heart,
                iconBold = R.drawable.icon_bold_heart
            ),
            NavigationModel(
                route = "account",
                name = "Tài khoản",
                icon = R.drawable.icon_outlined_profile,
                iconBold = R.drawable.icon_bold_profile
            )
        )
    }
}