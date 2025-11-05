package com.example.yakovleva_pr22

data class WeatherResponse(val name: String,
                           val weather:  List<Weather>?,
                           val main: Main?,
                           val wind: Wind?

)
