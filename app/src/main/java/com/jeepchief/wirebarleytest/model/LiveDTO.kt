package com.jeepchief.wirebarleytest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LiveDTO(

    @Expose
    @SerializedName("success")
    var success: Boolean,
    @Expose
    @SerializedName("timestamp")
    var timestamp: Int,
    @Expose
    @SerializedName("source")
    var source: String,
    @Expose
    @SerializedName("quotes")
    var quotes: Quotes
)

data class Quotes(
    @Expose
    @SerializedName("USDKRW")
    val USDKRW: Double,
    @Expose
    @SerializedName("USDJPY")
    val USDJPY: Double,
    @Expose
    @SerializedName("USDPHP")
    val USDPHP: Double,
    @Expose
    @SerializedName("USDAED")
    val USDAED: Double,
    @Expose
    @SerializedName("USDAFN")
    val USDAFN: Double,
    @Expose
    @SerializedName("USDALL")
    val USDALL: Double,
    @Expose
    @SerializedName("USDZMW")
    val USDZMW: Double,
    @Expose
    @SerializedName("USDZWL")
    val USDZWL: Double
)