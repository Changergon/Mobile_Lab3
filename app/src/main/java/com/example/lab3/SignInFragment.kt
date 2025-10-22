package com.example.lab3

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lab3.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private var registeredUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener("requestKey", this) { _, bundle ->
            Log.d("SignInFragment", "Получен результат от SignUpFragment. Bundle: $bundle")

            val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable("user", User::class.java)
            } else {
                @Suppress("DEPRECATION")
                bundle.getParcelable("user")
            }

            if (user != null) {
                Log.d("SignInFragment", "Успешно извлечен ОБЪЕКТ User: $user")
                registeredUser = user
                binding.emailEditText.setText(user.email)
                binding.passwordEditText.setText(user.password)
                Toast.makeText(requireContext(), "Привет, ${user.name}! Данные подставлены.", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("SignInFragment", "Объект User не найден. Попытка извлечь СТРОКИ.")
                val name = bundle.getString("name_string")
                val email = bundle.getString("email_string")
                val password = bundle.getString("password_string")
                if (name != null && email != null && password != null) {
                     Log.d("SignInFragment", "Успешно извлечены СТРОКИ.")
                    registeredUser = User(name, email, password)
                    binding.emailEditText.setText(email)
                    binding.passwordEditText.setText(password)
                    Toast.makeText(requireContext(), "Привет, $name! Данные (строки) подставлены.", Toast.LENGTH_SHORT).show()
                } else {
                     Log.e("SignInFragment", "Не удалось извлечь данные.")
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Пожалуйста, введите корректные данные", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (registeredUser != null && email == registeredUser?.email && password == registeredUser?.password) {
                Toast.makeText(requireContext(), "Вход успешен!", Toast.LENGTH_SHORT).show()
                (activity as? MainActivity)?.navigateTo(HomeFragment(), true)
            } else {
                Toast.makeText(requireContext(), "Неверный email или пароль", Toast.LENGTH_SHORT).show()
            }
        }

        binding.signUpText.setOnClickListener {
            (activity as? MainActivity)?.navigateTo(SignUpFragment(), false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
