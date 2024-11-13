package com.example.cc17_3f_secollesvn_act4

// MainActivity.kt
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val billAmountEditText = findViewById<EditText>(R.id.billAmountEditText)
        val serviceQualityRadioGroup = findViewById<RadioGroup>(R.id.serviceQualityRadioGroup)
        val roundUpSwitch = findViewById<Switch>(R.id.roundUpSwitch)
        val tipResultTextView = findViewById<TextView>(R.id.tipResultTextView)

        // Listener to update the tip calculation whenever the bill amount changes
        billAmountEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                calculateTip(billAmountEditText, serviceQualityRadioGroup, roundUpSwitch, tipResultTextView)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Listener for the radio group to recalculate tip when service quality changes
        serviceQualityRadioGroup.setOnCheckedChangeListener { _, _ ->
            calculateTip(billAmountEditText, serviceQualityRadioGroup, roundUpSwitch, tipResultTextView)
        }

        // Listener for the round-up toggle switch
        roundUpSwitch.setOnCheckedChangeListener { _, _ ->
            calculateTip(billAmountEditText, serviceQualityRadioGroup, roundUpSwitch, tipResultTextView)
        }
    }

    // Function to calculate and display the tip
    private fun calculateTip(billAmountEditText: EditText, serviceQualityRadioGroup: RadioGroup, roundUpSwitch: Switch, tipResultTextView: TextView) {
        val billAmount = billAmountEditText.text.toString().toDoubleOrNull()
        val tipPercentage = when (serviceQualityRadioGroup.checkedRadioButtonId) {
            R.id.amazingServiceRadioButton -> 20
            R.id.goodServiceRadioButton -> 18
            R.id.okServiceRadioButton -> 15
            else -> 0
        }

        if (billAmount != null && tipPercentage > 0) {
            var tipAmount = billAmount * (tipPercentage / 100.0)

            // Round up if the switch is enabled
            if (roundUpSwitch.isChecked) {
                tipAmount = ceil(tipAmount)
            }

            tipResultTextView.text = "Tip: $%.2f".format(tipAmount)
        } else {
            tipResultTextView.text = "Tip: $0.00"
        }
    }
}
