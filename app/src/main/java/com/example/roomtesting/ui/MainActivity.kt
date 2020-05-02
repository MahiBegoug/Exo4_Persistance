package com.example.roomtesting.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.roomtesting.R
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {


    private var compositeDisposable:CompositeDisposable?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navController = Navigation.findNavController(this,
            R.id.fragment
        )
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.fragment),
            null
        )
    }


}
