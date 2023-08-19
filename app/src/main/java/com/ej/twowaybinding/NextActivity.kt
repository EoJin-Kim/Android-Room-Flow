package com.ej.twowaybinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.ej.twowaybinding.databinding.ActivityMainBinding
import com.ej.twowaybinding.databinding.ActivityNextBinding
import com.ej.twowaybinding.db.MyDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NextActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNextBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_next)

        binding.lifecycleOwner = this

        val db = MyDatabase.getDatabase(this)

        val recyclerView = binding.userRV
        val myListAdapter = MyListAdapter()

        recyclerView.adapter = myListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        binding.read.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().getAllDataWithFlow().collect{
                    withContext(Dispatchers.Main){
                        myListAdapter.submitList(it)
                    }
                }
            }
        }

        binding.update.setOnClickListener {
            val id = binding.id.text.toString().toInt()

            CoroutineScope(Dispatchers.IO).launch {
                val result = db.userDao().getAllData()
                val userEntity = result[id]

                userEntity.name = "updated 된 id"
                userEntity.age = 0

                db.userDao().update(userEntity)
            }
        }

        binding.remove.setOnClickListener {
            val id = binding.id.text.toString().toInt()

            CoroutineScope(Dispatchers.IO).launch {
                val result = db.userDao().getAllData()
                val userEntity = result[id]

                db.userDao().delete(userEntity)
            }
        }
    }
}