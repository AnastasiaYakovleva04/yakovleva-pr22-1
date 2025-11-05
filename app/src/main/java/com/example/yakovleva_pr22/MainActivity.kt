package com.example.yakovleva_pr22

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    val key = "d8c0538ed08eb5827c6ba3c7e334214a"
    private lateinit var cityEditText: EditText
    private lateinit var getWeatherButton: Button
    private lateinit var weatherTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityEditText = findViewById(R.id.cityEditText)
        getWeatherButton = findViewById(R.id.getWeatherButton)
        weatherTextView = findViewById(R.id.weatherTextView)

        getWeatherButton.setOnClickListener {
            val cityName = cityEditText.text.toString()
            if (cityName.isNotEmpty()) {
                getWeatherData(cityName)
            } else {
                weatherTextView.text = "Введите название города"
            }
        }
    }
    private fun getWeatherData(cityName: String) {
        val url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + key + "&units=metric&lang=ru"
        val queue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                Log.d("WEATHER_APP", "JSON Response: $response")
                val weatherResponse = Gson().fromJson(response, WeatherResponse::class.java)
                val city = weatherResponse.name
                val temp = weatherResponse.main?.temp
                val desc = weatherResponse.weather?.firstOrNull()?.description
                val main = weatherResponse.weather?.firstOrNull()?.main
                val pressure = weatherResponse.main?.pressure
                val pressureMmHg = pressure?.times(0.75)?.toInt()
                val windSpeed = weatherResponse.wind?.speed

                Log.i("WEATHER_APP", "Город: $city")
                Log.i("WEATHER_APP", "Температура: $temp °C")
                Log.i("WEATHER_APP", "Погода: $main")
                Log.i("WEATHER_APP", "Описание: $desc")
                Log.i("WEATHER_APP", "Давление: $pressureMmHg мм рт ст")
                Log.i("WEATHER_APP", "Скорость ветра: $windSpeed м/с")

                val weatherInfo = """
                Город: $city
                Температура: $temp °C
                Погода: $main
                Описание: $desc
                Давление: $pressureMmHg мм рт ст
                Скорость ветра: $windSpeed м/с
            """.trimIndent()
                weatherTextView.text = weatherInfo
            },
            { error ->
                Log.e("WEATHER_APP", "Ошибка: ${error.message}")
                weatherTextView.text = "Ошибка: ${error.message}"
            }
        )
        queue.add(request)
    }
}

