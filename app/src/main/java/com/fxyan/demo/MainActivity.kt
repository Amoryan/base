package com.fxyan.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.fxyan.widget.IphoneStyleDialog
import com.fxyan.widget.IphoneStylePopWindow
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val a1 = arrayListOf("1", "2", "3", "4", "5")

        val window = IphoneStylePopWindow(this@MainActivity)
        window.setOnItemClickListener { obj, _ -> Toast.makeText(this@MainActivity, obj, Toast.LENGTH_SHORT).show() }

        textView.setOnClickListener { window.show(textView, a1) }

        textView1.setOnClickListener {
            IphoneStyleDialog.Builder(this@MainActivity)
                .setTitle("提示")
                .setContent("内容", "#1b8fe6")
                .setNegativeButton("取消", "#1b8fe6") {
                    Toast.makeText(this@MainActivity, "取消", Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("确定", "#1b8fe6") {
                    Toast.makeText(this@MainActivity, "确定", Toast.LENGTH_SHORT).show()
                }
                .setCancelable(false)
                .show()
        }
    }
}
