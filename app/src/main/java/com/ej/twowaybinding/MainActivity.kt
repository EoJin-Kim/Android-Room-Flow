package com.ej.twowaybinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ej.twowaybinding.databinding.ActivityMainBinding
import com.ej.twowaybinding.db.TextDatabase
import com.ej.twowaybinding.db.TextEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.vm =  viewModel
        binding.lifecycleOwner = this

        val inputArea = binding.textInputArea
        val insertBtn = binding.insert
        val getAllBtn = binding.getData
        val deleteBtn = binding.delete

        val resultArea = binding.resultArea

        val db = TextDatabase.getDatabase(this)

        insertBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val text = TextEntity(0,inputArea.text.toString())
                db.textDao().insert(text)

                inputArea.setText("")
            }
        }

//        getAllBtn.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                val resultText = db.textDao().getAllData().toString()
//                withContext(Dispatchers.Main){
//                    resultArea.text = resultText
//                }
//            }
//        }
        CoroutineScope(Dispatchers.IO).launch {
            db.textDao().getAllDataFlow().collect{
                val resultText = it.toString()
                withContext(Dispatchers.Main){
                    resultArea.text = resultText
                }
            }
        }


        deleteBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.textDao().deleteAllData()
            }
        }

    }

}