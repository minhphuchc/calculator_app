// MainActivity.kt
package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var resultTextView: TextView
    private var currentInput = StringBuilder()
    private var lastResult = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)

        // Number buttons
        setupButton(R.id.button0, "0")
        setupButton(R.id.button1, "1")
        setupButton(R.id.button2, "2")
        setupButton(R.id.button3, "3")
        setupButton(R.id.button4, "4")
        setupButton(R.id.button5, "5")
        setupButton(R.id.button6, "6")
        setupButton(R.id.button7, "7")
        setupButton(R.id.button8, "8")
        setupButton(R.id.button9, "9")
        setupButton(R.id.buttonDot, ".")

        // Operator buttons
        setupButton(R.id.buttonAdd, "+")
        setupButton(R.id.buttonSubtract, "-")
        setupButton(R.id.buttonMultiply, "×")
        setupButton(R.id.buttonDivide, "÷")

        // Other buttons
        val buttonClear = findViewById<Button>(R.id.buttonClear)
        buttonClear.setOnClickListener {
            currentInput.clear()
            updateDisplay()
        }

        val buttonClearEntry = findViewById<Button>(R.id.buttonClearEntry)
        buttonClearEntry.setOnClickListener {
            if (currentInput.isNotEmpty()) {
                currentInput.deleteCharAt(currentInput.length - 1)
                updateDisplay()
            }
        }

        val buttonBackspace = findViewById<Button>(R.id.buttonBackspace)
        buttonBackspace.setOnClickListener {
            if (currentInput.isNotEmpty()) {
                currentInput.deleteCharAt(currentInput.length - 1)
                updateDisplay()
            }
        }

        val buttonPlusMinus = findViewById<Button>(R.id.buttonPlusMinus)
        buttonPlusMinus.setOnClickListener {
            if (currentInput.isNotEmpty()) {
                if (currentInput.startsWith("-")) {
                    currentInput.deleteCharAt(0)
                } else {
                    currentInput.insert(0, "-")
                }
                updateDisplay()
            }
        }

        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
        buttonEquals.setOnClickListener {
            try {
                val expression = currentInput.toString()
                    .replace("×", "*")
                    .replace("÷", "/")

                val result = ExpressionBuilder(expression).build().evaluate()

                // Format result to avoid unnecessary decimal places
                lastResult = if (result == result.toLong().toDouble()) {
                    result.toLong().toString()
                } else {
                    result.toString()
                }

                currentInput.clear()
                currentInput.append(lastResult)
                updateDisplay()
            } catch (e: Exception) {
                resultTextView.text = "Error"
            }
        }
    }

    private fun setupButton(buttonId: Int, value: String) {
        val button = findViewById<Button>(buttonId)
        button.setOnClickListener {
            currentInput.append(value)
            updateDisplay()
        }
    }

    private fun updateDisplay() {
        resultTextView.text = if (currentInput.isEmpty()) "0" else currentInput.toString()
    }
}