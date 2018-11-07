package com.fxyan.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fxyan.widget.IphonePopWindow
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val a1 = arrayListOf("1", "2", "3", "4", "5")
        val a2 = arrayListOf("1", "2", "3", "4", "5", "1", "2", "3", "4", "5")

        val window = IphonePopWindow(this@MainActivity)
        window.setOnItemClickListener { obj, _ -> Toast.makeText(this@MainActivity, obj, Toast.LENGTH_SHORT).show() }

        textView.setOnClickListener { window.show(textView, a1) }

        textView1.setOnClickListener { window.show(textView1, a2) }
    }
}
