package com.aotuman.studydemo.lifecycles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.aotuman.studydemo.R
import com.aotuman.studydemo.lifecycles.bean.Person
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LCMainActivity : AppCompatActivity() {

    private lateinit var viewModel: PersonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_cycles_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        viewModel = ViewModelProvider(this).get(PersonViewModel::class.java)
        val navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            setVal()
        }
    }

    private fun setVal () {
        var personList = viewModel.personValue.value
        if (personList == null) personList = mutableListOf<Person>()
        var addList = mutableListOf<Person>()
        val addSize = 10000
        for (i in personList.size until personList.size + addSize) {
            var person = Person()
            person.name = "name:$i"
            person.sex = if (i%2==0) "男" else "女"
            addList.add(person)
        }
        personList.addAll(addList)
        viewModel.personValue.value = personList
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.fragment)
        return navController.navigateUp()
    }
}