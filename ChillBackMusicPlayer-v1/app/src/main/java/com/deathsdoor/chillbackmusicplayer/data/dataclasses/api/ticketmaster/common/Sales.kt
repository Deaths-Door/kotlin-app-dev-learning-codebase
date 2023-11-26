package com.deathsdoor.chillbackmusicplayer.data.dataclasses.api.ticketmaster.common

import com.google.gson.annotations.SerializedName

data class Sales(

    @field:SerializedName("public")
    val public: PublicSales,

    @field:SerializedName("presales")
    val presales: List<PreSales>
)