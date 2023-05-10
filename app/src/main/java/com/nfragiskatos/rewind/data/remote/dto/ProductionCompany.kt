package com.nfragiskatos.rewind.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductionCompany(
    val id: Int,
    @SerializedName("logo_path")
    val logo_path: String,
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String
)