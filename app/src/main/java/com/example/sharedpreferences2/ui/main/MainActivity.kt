package com.example.sharedpreferences2.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.sharedpreferences2.R
import com.example.sharedpreferences2.data.repository.UserRepository
import com.example.sharedpreferences2.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


       //instanciar a viewModel usando a factory
        val repository = UserRepository(applicationContext)
        val myFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, myFactory).get(MainViewModel::class.java)

        setupObservers()
        setupListeners()
    }
    private fun setupListeners(){
        binding.buttomSave.setOnClickListener{
            val str = binding.editName.text.toString()
            viewModel.save(str)
        }
    }

    private fun setupObservers(){
        lifecycleScope.launch{
            viewModel.username.collect{
                if(it.isNullOrBlank()){
                    binding.textMesage.text = "Bem vindo, usuario!"
                    binding.buttomSave.text = "salvar"
                }else{
                    binding.textMesage.text = "bem vindo de volta, $it! "
                    binding.buttomSave.text = "Alterar"
                }
            }
        }
    }
}