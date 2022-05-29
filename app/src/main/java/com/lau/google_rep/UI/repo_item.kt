package com.lau.google_rep.UI

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lau.google_rep.R
import com.squareup.picasso.Picasso

class repo_item : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_item)

        val bundle : Bundle ?= intent.extras

        val name = bundle!!.getString("name")
        val avatar_url = bundle!!.getString("avatar_url")
        val created_at = bundle!!.getString("created_at")
        val stargazers_count = bundle!!.getString("stargazers_count")

        val nametext = findViewById<TextView>(R.id.name)
        val image : ImageView = findViewById(R.id.image_rep)
        val created = findViewById<TextView>(R.id.created)
        val count = findViewById<TextView>(R.id.stargazers_count)

        nametext.setText(name)
        Picasso.get().load(avatar_url).into(image)
        created.setText(created_at)
        count.setText(stargazers_count)
        val back = findViewById<ImageView>(R.id.leftIco)

        back.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                startActivity(Intent(applicationContext,MainActivity::class.java))
            }

        })

    }
}