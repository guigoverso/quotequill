package com.guilhermeoccorona.quotequill

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var background: ImageView
    private lateinit var quoteTextView: TextView
    private lateinit var authorTextView: TextView

    private lateinit var quoteInput: TextInputEditText
    private lateinit var hideAuthorSwitch: SwitchCompat
    private lateinit var authorInputLayout: TextInputLayout
    private lateinit var authorInput: TextInputEditText
    private lateinit var backwardBackgroundButton: Button
    private lateinit var fowardBackgroundButton: Button
    private lateinit var backgroundName: TextView

    private val nameKey = "name"
    private val imageKey = "image"

    private var backgroundList = listOf(
        mapOf(nameKey to "Paper", imageKey to R.drawable.paper),
        mapOf(nameKey to "Universe", imageKey to R.drawable.universe),
        mapOf(nameKey to "Love", imageKey to R.drawable.love),
    )

    var backgroundListPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureQuoteViewSize()
        assignComponents()
        changeBackground(0)
        configureInputs()
    }

    private fun assignComponents() {
        background = findViewById(R.id.background)
        quoteTextView = findViewById(R.id.quote)
        authorTextView = findViewById(R.id.author)
        quoteInput = findViewById(R.id.quoteInput)
        hideAuthorSwitch = findViewById(R.id.hideAuthorSwitch)
        authorInput = findViewById(R.id.authorInput)
        backwardBackgroundButton = findViewById(R.id.backwardButton)
        fowardBackgroundButton = findViewById(R.id.fowardButton)
        backgroundName = findViewById(R.id.backgroundName)
        authorInputLayout = findViewById(R.id.authorInputLayout)
    }

    private fun configureInputs() {
        configureQuoteInput()
        configureAuthorInput()
        configureHideAuthorInput()
        configureBackgroundButtons()
    }

    private fun configureHideAuthorInput() {
        hideAuthorSwitch.setOnCheckedChangeListener { _, isChecked ->
            val visibility : Int = if(isChecked) {
                View.GONE
            } else {
                View.VISIBLE
            }
            authorInputLayout.visibility = visibility
            authorInput.visibility = visibility
            authorTextView.visibility = visibility
        }
    }

    private fun configureBackgroundButtons() {
        fowardBackgroundButton.setOnClickListener {
            backgroundListPosition++
            if(backgroundListPosition >= backgroundList.size) {
                backgroundListPosition = 0
            }
            changeBackground(backgroundListPosition)
        }
        backwardBackgroundButton.setOnClickListener {
            backgroundListPosition--
            if(backgroundListPosition < 0) {
                backgroundListPosition = backgroundList.size - 1
            }
            changeBackground(backgroundListPosition)
        }
    }

    private fun changeBackground(position: Int) {
        val att = backgroundList[position]
        background.setImageResource(att[imageKey] as Int)
        backgroundName.text = att[nameKey].toString()
        changeTextColor(position)
    }

    private fun changeTextColor(position: Int) {
        val white = ContextCompat.getColor(this, R.color.white)
        val black = ContextCompat.getColor(this, R.color.black)
        if(position == 1) {
            quoteTextView.setTextColor(white)
            authorTextView.setTextColor(white)
        } else {
            quoteTextView.setTextColor(black)
            authorTextView.setTextColor(black)
        }
    }

    private fun configureAuthorInput() {
        authorInput.addTextChangedListener {
            authorTextView.text = it
        }
    }

    private fun configureQuoteInput() {
        quoteInput.addTextChangedListener {
            quoteTextView.text = it
        }
    }

    private fun configureQuoteViewSize() {
        val layout = findViewById<FrameLayout>(R.id.quoteLayout)
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val updatedLayoutParams = layout.layoutParams
        updatedLayoutParams.height = screenWidth
        layout.layoutParams = updatedLayoutParams
    }
}