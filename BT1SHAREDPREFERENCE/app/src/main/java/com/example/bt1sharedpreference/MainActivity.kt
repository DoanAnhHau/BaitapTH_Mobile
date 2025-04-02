package com.example.bt1sharedpreference

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bt1sharedpreference.ui.theme.BT1SHAREDPREFERENCETheme

class MainActivity : ComponentActivity() {
    private lateinit var btnsave: Button
    private lateinit var btnclear: Button
    private lateinit var btndisplay: Button
    private lateinit var edtUser : EditText
    private lateinit var edtPass : EditText
    private lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // tham chieu cac phan tu View thuc te trong giao dien
        btnsave = findViewById(R.id.btnLuu)
        btnclear = findViewById(R.id.btnXoa)
        btndisplay = findViewById(R.id.btnHienthi)

        edtUser = findViewById(R.id.etTenNguoidung)
        edtPass = findViewById(R.id.etPassword)

        // khoi tao PreferenceHelper
        preferenceHelper = PreferenceHelper(this)
        btnsave.setOnClickListener {
            val user = edtUser.text.toString()
            val pass = edtPass.text.toString()
            preferenceHelper.saveData("username", user)
            preferenceHelper.saveData("password", pass)
        }
        btnclear.setOnClickListener {
            preferenceHelper.clearData()
            edtUser.text.clear()
            edtPass.text.clear()
        }
        btndisplay.setOnClickListener {
            val user = preferenceHelper.getData("username")
            val pass = preferenceHelper.getData("password")
            edtUser.setText(user)
            edtPass.setText(pass)

        }


    }
}