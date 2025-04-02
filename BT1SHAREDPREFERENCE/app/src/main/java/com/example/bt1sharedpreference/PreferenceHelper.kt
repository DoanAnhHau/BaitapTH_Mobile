package com.example.bt1sharedpreference

import android.content.Context
//getSharedPreferences: Dùng để truy cập hoặc tạo file lưu trữ SharedPreferences
//edit(): Sử dụng để thêm, chỉnh sửa hoặc xóa dữ liệu.
class PreferenceHelper (context : Context){
    private val preference = context.getSharedPreferences("MyPreference", Context.MODE_PRIVATE)
    fun saveData(key : String, value : String){
        val editor = preference.edit()
        editor.putString(key, value)
        editor.apply()
    }
    fun getData(key : String) : String?{
        return preference.getString(key, null)
    }

    fun clearData(){
        val editor = preference.edit()
        editor.clear()
        editor.apply()
    }

    fun removeData(key : String){
        val editor = preference.edit()
        editor.remove(key)
        editor.apply()
    }


}