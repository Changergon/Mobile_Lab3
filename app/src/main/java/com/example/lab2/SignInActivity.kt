package com.example.lab2

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log // <--- ДОБАВЛЕНО
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lab2.databinding.ActivitySignInBinding

class SignInActivity : BaseActivity() { // BaseActivity уже предоставляет TAG

    private lateinit var binding: ActivitySignInBinding
    private var registeredUser: User? = null

    private val signUpActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d(TAG, "Получен результат от SignUpActivity. Код результата: ${result.resultCode}")

        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            Log.d(TAG, "Результат OK. Входящий Intent: $data")
            Log.d(TAG, "Extras в Intent: ${data?.extras?.keySet()?.joinToString()}")

            val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                data?.getParcelableExtra(SignUpActivity.EXTRA_USER_OBJECT, User::class.java)
            } else {
                @Suppress("DEPRECATION")
                data?.getParcelableExtra<User>(SignUpActivity.EXTRA_USER_OBJECT)
            }

            if (user != null) {
                Log.d(TAG, "Успешно извлечен ОБЪЕКТ User: $user")
                registeredUser = user
                Toast.makeText(this, "Привет, ${registeredUser?.name}! Данные для входа подставлены.", Toast.LENGTH_LONG).show()
                binding.emailEditText.setText(registeredUser?.email)
                binding.passwordEditText.setText(registeredUser?.password)
            } else {
                Log.d(TAG, "Объект User не найден. Попытка извлечь данные как СТРОКИ.")
                val nameString = data?.getStringExtra(SignUpActivity.EXTRA_USER_NAME_STRING)
                val emailString = data?.getStringExtra(SignUpActivity.EXTRA_USER_EMAIL_STRING)
                val passwordString = data?.getStringExtra(SignUpActivity.EXTRA_USER_PASSWORD_STRING)

                if (nameString != null && emailString != null && passwordString != null) {
                    Log.d(TAG, "Успешно извлечены СТРОКИ: Имя=$nameString, Email=$emailString, Пароль=$passwordString")
                    registeredUser = User(nameString, emailString, passwordString)
                    Toast.makeText(this, "Привет, ${registeredUser?.name}! Данные (строки) подставлены.", Toast.LENGTH_LONG).show()
                    binding.emailEditText.setText(registeredUser?.email)
                    binding.passwordEditText.setText(registeredUser?.password)
                } else {
                    Log.e(TAG, "Не удалось извлечь данные о регистрации ни как объект, ни как строки.")
                    Toast.makeText(this, "Не удалось получить данные о регистрации", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (result.resultCode == Activity.RESULT_CANCELED) {
            Log.d(TAG, "Регистрация отменена пользователем.")
            Toast.makeText(this, "Регистрация отменена", Toast.LENGTH_SHORT).show()
        }
    }

    // ... (остальная часть класса без изменений) ...
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Поля Email и пароль не могут быть пустыми", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Пожалуйста, введите корректный адрес электронной почты", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (registeredUser != null) {
                Log.d(TAG, "Проверка входа. Введено: Email=$email, Пароль=$password. Зарегистрировано: Email=${registeredUser?.email}, Пароль=${registeredUser?.password}")
                if (email == registeredUser?.email && password == registeredUser?.password) {
                    Log.d(TAG, "Вход успешен для пользователя: ${registeredUser?.name}")
                    Toast.makeText(this, "Вход успешен! Добро пожаловать, ${registeredUser?.name}!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    Log.w(TAG, "Неверный email или пароль.")
                    Toast.makeText(this, "Неверный email или пароль.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.w(TAG, "Попытка входа без зарегистрированного пользователя (registeredUser is null).")
                Toast.makeText(this, "Пожалуйста, сначала зарегистрируйтесь.", Toast.LENGTH_LONG).show()
            }
        }

        binding.signUpText.setOnClickListener {
            Log.d(TAG, "Запуск SignUpActivity для получения результата...")
            val intent = Intent(this, SignUpActivity::class.java)
            signUpActivityResultLauncher.launch(intent)
        }
    }
}
