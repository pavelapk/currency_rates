package ru.pavelapk.currency_rates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.pavelapk.currency_rates.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }




}