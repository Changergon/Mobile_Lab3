package com.example.lab2

import android.content.Intent
import android.os.Bundle
// AppCompatActivity больше не нужен напрямую, так как BaseActivity от него наследуется
import com.example.lab2.databinding.ActivityOnboardBinding

class OnboardActivity : BaseActivity() { // ИЗМЕНЕНО

    private lateinit var binding: ActivityOnboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Вызов super.onCreate() из BaseActivity выполнит логирование
        binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextButton.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            // Требование B1: Кнопка на OnboardActivity открывает SignInActivity - уже реализовано.
        }
    }
}
