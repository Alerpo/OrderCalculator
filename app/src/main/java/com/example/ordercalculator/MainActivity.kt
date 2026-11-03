package com.example.ordercalculator

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etOrderSum: EditText
    private lateinit var etDishesCount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var rgDiscount: RadioGroup
    private lateinit var rbDiscount3: RadioButton
    private lateinit var rbDiscount5: RadioButton
    private lateinit var rbDiscount7: RadioButton
    private lateinit var rbDiscount10: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupListeners()
    }

    private fun initViews() {
        etOrderSum = findViewById(R.id.etOrderSum)
        etDishesCount = findViewById(R.id.etDishesCount)
        seekBarTip = findViewById(R.id.seekBarTip)
        rgDiscount = findViewById(R.id.rgDiscount)
        rbDiscount3 = findViewById(R.id.rbDiscount3)
        rbDiscount5 = findViewById(R.id.rbDiscount5)
        rbDiscount7 = findViewById(R.id.rbDiscount7)
        rbDiscount10 = findViewById(R.id.rbDiscount10)
    }

    private fun setupListeners() {
        etDishesCount.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                calculateDiscount()
            }
        }

        etDishesCount.setOnEditorActionListener { _, _, _ ->
            calculateDiscount()
            false
        }
    }

    private fun calculateDiscount() {
        val dishesCount = etDishesCount.text.toString().toIntOrNull() ?: 0

        when {
            dishesCount in 1..2 -> rbDiscount3.isChecked = true
            dishesCount in 3..5 -> rbDiscount5.isChecked = true
            dishesCount in 6..10 -> rbDiscount7.isChecked = true
            dishesCount > 10 -> rbDiscount10.isChecked = true
            else -> rgDiscount.clearCheck()
        }
    }
}