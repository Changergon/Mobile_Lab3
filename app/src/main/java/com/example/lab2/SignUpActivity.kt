package com.example.lab2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log // <--- ДОБАВЛЕНО
import android.widget.RadioButton
import android.widget.Toast
import com.example.lab2.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity() { // BaseActivity уже предоставляет TAG

    private lateinit var binding: ActivitySignUpBinding

    companion object {
        const val EXTRA_USER_NAME_STRING = "com.example.lab2.EXTRA_USER_NAME_STRING"
        const val EXTRA_USER_EMAIL_STRING = "com.example.lab2.EXTRA_USER_EMAIL_STRING"
        const val EXTRA_USER_PASSWORD_STRING = "com.example.lab2.EXTRA_USER_PASSWORD_STRING"
        const val EXTRA_USER_OBJECT = "com.example.lab2.EXTRA_USER_OBJECT"
    }

    private val sendUserObject = true // <--- ИЗМЕНИТЕ ЭТОТ ФЛАГ ДЛЯ ВЫБОРА

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpButton.setOnClickListener {
            // ... (валидация данных без изменений) ...
            val name = binding.nameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()
            val ageString = binding.ageEditText.text.toString().trim()
            val selectedGenderId = binding.genderRadioGroup.checkedRadioButtonId
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || ageString.isEmpty()) {
                Toast.makeText(this, "Все поля, включая возраст, должны быть заполнены", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (selectedGenderId == -1) {
                Toast.makeText(this, "Пожалуйста, выберите пол", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val age = ageString.toIntOrNull()
            if (age == null || age <= 0) {
                Toast.makeText(this, "Пожалуйста, введите корректный возраст", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(this, "Неверный формат электронной почты", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.length < 6){
                Toast.makeText(this, "Пароль должен содержать не менее 6 символов", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val resultIntent = Intent()

            if (sendUserObject) {
                Toast.makeText(this, "Регистрация успешна! Передаем ОБЪЕКТ User...", Toast.LENGTH_SHORT).show()
                val user = User(name = name, email = email, password = password)

                // ЛОГИРОВАНИЕ ОТПРАВКИ ОБЪЕКТА
                Log.d(TAG, "Отправка данных как ОБЪЕКТ User. Данные: $user")

                resultIntent.putExtra(EXTRA_USER_OBJECT, user)
            } else {
                Toast.makeText(this, "Регистрация успешна! Передаем СТРОКИ...", Toast.LENGTH_SHORT).show()

                // ЛОГИРОВАНИЕ ОТПРАВКИ СТРОК
                Log.d(TAG, "Отправка данных как СТРОКИ.")
                Log.d(TAG, " -> Имя: $name")
                Log.d(TAG, " -> Email: $email")
                // В реальном приложении пароли никогда не следует логировать!
                // Делаем это исключительно в учебных целях.
                Log.d(TAG, " -> Пароль: $password")

                resultIntent.putExtra(EXTRA_USER_NAME_STRING, name)
                resultIntent.putExtra(EXTRA_USER_EMAIL_STRING, email)
                resultIntent.putExtra(EXTRA_USER_PASSWORD_STRING, password)
            }

            Log.d(TAG, "Установка результата: RESULT_OK и отправка Intent.")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        binding.signInText.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}
