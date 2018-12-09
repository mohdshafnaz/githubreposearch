package com.example.shaffz.mvvmgettest.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.shaffz.mvvmgettest.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        setContentView(R.layout.activity_detail)
        detail_rating.text = "${Constants.ID.capitalize()}: ${bundle.getString(Constants.ID)}"
        detail_title.text = "${Constants.NAME.capitalize()}: ${bundle.getString(Constants.NAME)}"
        detail_year.text = "${Constants.DESCRIPTION.capitalize()}: ${bundle.getString(Constants.DESCRIPTION)}"
        detail_date.text = "${Constants.TYPE.capitalize()}: ${bundle.getString(Constants.TYPE)}"
        detail_back.setOnClickListener { onBackPressed() }
    }

    object Constants {
        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val TYPE = "type"
    }
}