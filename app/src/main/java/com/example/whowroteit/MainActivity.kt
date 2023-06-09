package com.example.whowroteit

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.asynctaskloaderdemo.R
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {
    private lateinit var mBookInput: EditText
    private lateinit var mTitleText: TextView
    private lateinit var mAuteurText: TextView
    private lateinit var mSearchBtn: Button
    private lateinit var mThumbnailImg: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mBookInput = findViewById(R.id.bookInput)
        mTitleText = findViewById(R.id.titleText)
        mAuteurText = findViewById(R.id.authorText)
        mSearchBtn = findViewById(R.id.searchButton)
        mThumbnailImg = findViewById(R.id.thumbnail)

        mSearchBtn.setOnClickListener {
            searchBooks(it)
        }

        mBookInput.setOnKeyListener { view, i, keyEvent -> if (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER){
            mSearchBtn.performClick()
        }
            true
        }

    }



//  salam

    private fun searchBooks(view: View) {
        val q = mBookInput.text.toString()
        if (q.isEmpty()) {
            Toast.makeText(this, "enter a book name", Toast.LENGTH_SHORT).show()
            mAuteurText.text = getString(R.string.no_search_term)
            mAuteurText.visibility = View.VISIBLE
            return
        }
        val inputManager: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }



        if (!NetworkUtils.isOnline(this)) {
            Toast.makeText(this, "connect to the internet!", Toast.LENGTH_SHORT).show()
            return
        }

        FetchBook(mTitleText, mAuteurText, mThumbnailImg).execute(q)

    }
}