// MainActivity.kt
package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var resultTextView: TextView
    private lateinit var expressionTextView: TextView
    private var currentInput = StringBuilder()
    private var lastResult = ""
    private var isNewCalculation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)
        expressionTextView = findViewById(R.id.expressionTextView)

        expressionTextView.text = ""

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
            isNewCalculation = true
            expressionTextView.text = ""
            resultTextView.text = "0"
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
            calculateResult()
        }
    }

    private fun setupButton(buttonId: Int, value: String) {
        val button = findViewById<Button>(buttonId)
        button.setOnClickListener {
            if (isNewCalculation && (value == "+" || value == "-" || value == "×" || value == "÷")) {
                currentInput.append(lastResult)
                isNewCalculation = false
            } else if (isNewCalculation) {
                currentInput.clear()
                isNewCalculation = false
            }
            currentInput.append(value)
            updateDisplay()
        }
    }

    private fun updateDisplay() {
        // Hiển thị dữ liệu hiện tại trong resultTextView
        if (currentInput.isEmpty()) {
            expressionTextView.text = ""
            resultTextView.text = "0"
        } else {
            resultTextView.text = currentInput.toString()
        }
    }

    private fun calculateResult() {
        try {
            if (currentInput.isEmpty()) return

            val expression = currentInput.toString()
                .replace("×", "*")
                .replace("÷", "/")

            val expressionToShow = currentInput.toString() + " ="

            val result = ExpressionBuilder(expression).build().evaluate()

            // Format result to avoid unnecessary decimal places
            lastResult = if (result == result.toLong().toDouble()) {
                result.toLong().toString()
            } else {
                result.toString()
            }

            // Hiển thị biểu thức và kết quả
            expressionTextView.text = expressionToShow
            resultTextView.text = lastResult

            // Clear the current input but keep the result for potential further calculations
            currentInput.clear()
            isNewCalculation = true

        } catch (e: Exception) {
            expressionTextView.text = ""
            resultTextView.text = "Error"
            currentInput.clear()
            isNewCalculation = true
        }
    }
}