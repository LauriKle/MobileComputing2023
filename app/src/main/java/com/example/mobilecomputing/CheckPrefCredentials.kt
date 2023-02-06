package com.example.mobilecomputing

import android.content.Context
import android.os.Bundle

class CheckPrefCredentials(context: Context){
    fun createAccount(context: Context, givenUsername:String, givenPassword:String){
        val sharedPreferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()
        editor.putString("username", givenUsername)
        editor.putString("password", givenPassword)
        editor.commit()
    }

    fun checkCredentials(context: Context, givenUsername:String, givenPassword:String) : Boolean {
        val sharedPreferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()
        var usr = sharedPreferences.getString("username", "")
        var pwd = sharedPreferences.getString("password", "")
        println("getattiin username $usr")
        println("getattiin password $pwd")
        println("given username $givenUsername")
        println("given password $givenPassword")
        val succ: Boolean = (pwd == givenPassword && usr == givenUsername)
        return succ
    }

    fun getUsername(context: Context) : String {
        val sharedPreferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()
        var usr = sharedPreferences.getString("username", "")
        println("getattiin username $usr")
        return usr.toString()
    }

}