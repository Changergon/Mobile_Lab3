package com.example.lab3

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.lab3.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val sendUserObject = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpButton.setOnClickListener {
            if (!validateInput()) {
                return@setOnClickListener
            }

            val name = binding.nameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()

            if (sendUserObject) {
                Toast.makeText(requireContext(), "Регистрация успешна! Передаем ОБЪЕКТ User...", Toast.LENGTH_SHORT).show()
                val user = User(name, email, password)
                setFragmentResult("requestKey", bundleOf("user" to user))
            } else {
                Toast.makeText(requireContext(), "Регистрация успешна! Передаем СТРОКИ...", Toast.LENGTH_SHORT).show()
                val bundle = Bundle().apply {
                    putString("name_string", name)
                    putString("email_string", email)
                    putString("password_string", password)
                }
                setFragmentResult("requestKey", bundle)
            }
            
            parentFragmentManager.popBackStack()
        }

        binding.signInText.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
    
    private fun validateInput(): Boolean {
        val name = binding.nameEditText.text.toString().trim()
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString()
        val ageString = binding.ageEditText.text.toString().trim()
        val selectedGenderId = binding.genderRadioGroup.checkedRadioButtonId

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || ageString.isEmpty()) {
            Toast.makeText(requireContext(), "Все поля, включая возраст, должны быть заполнены", Toast.LENGTH_SHORT).show()
            return false
        }
        if (selectedGenderId == -1) {
            Toast.makeText(requireContext(), "Пожалуйста, выберите пол", Toast.LENGTH_SHORT).show()
            return false
        }
        val age = ageString.toIntOrNull()
        if (age == null || age <= 0) {
            Toast.makeText(requireContext(), "Пожалуйста, введите корректный возраст", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(requireContext(), "Неверный формат электронной почты", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.length < 6){
            Toast.makeText(requireContext(), "Пароль должен содержать не менее 6 символов", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
