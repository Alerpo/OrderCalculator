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
    private lateinit var tvTipMin: TextView
    private lateinit var tvTipMax: TextView
    private lateinit var tvTipPercent: TextView
    private lateinit var tvTipAmount: TextView

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
        tvTipMin = findViewById(R.id.tvTipMin)
        tvTipMax = findViewById(R.id.tvTipMax)
        tvTipPercent = findViewById(R.id.tvTipPercent)
        tvTipAmount = findViewById(R.id.tvTipAmount)
    }

    private fun calculateTipAmount() {
        val orderSum = etOrderSum.text.toString().toDoubleOrNull() ?: 0.0
        val tipPercent = seekBarTip.progress
        val tipAmount = orderSum * tipPercent / 100

        tvTipAmount.text = "Сумма чаевых: ${"%.2f".format(tipAmount)} ₽"
    }

    private fun setupListeners() {
        // Слайдер чаевых
        seekBarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvTipMin.text = progress.toString()
                tvTipPercent.text = "Чаевые: $progress%"
                calculateTipAmount()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Расчет скидки при изменении количества блюд
        etDishesCount.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                calculateDiscount()
            }
        }

        etDishesCount.setOnEditorActionListener { _, _, _ ->
            calculateDiscount()
            false
        }

        // Отключаем ручной выбор радиокнопок
        rgDiscount.setOnCheckedChangeListener { _, _ ->
            calculateDiscount()
        }
    }

    private fun calculateDiscount() {
        val dishesCount = etDishesCount.text.toString().toIntOrNull() ?: 0

        // Отключаем возможность ручного выбора
        rbDiscount3.isEnabled = false
        rbDiscount5.isEnabled = false
        rbDiscount7.isEnabled = false
        rbDiscount10.isEnabled = false

        when {
            dishesCount in 1..2 -> rbDiscount3.isChecked = true
            dishesCount in 3..5 -> rbDiscount5.isChecked = true
            dishesCount in 6..10 -> rbDiscount7.isChecked = true
            dishesCount > 10 -> rbDiscount10.isChecked = true
            else -> rgDiscount.clearCheck()
        }
    }
}