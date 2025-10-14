package com.example.lab3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab3.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.setSupportActionBar(binding.topAppBar)

        setupRecyclerView()
        loadDummyData()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        homeAdapter = HomeAdapter(emptyList())
        binding.recyclerView.adapter = homeAdapter
    }

    private fun loadDummyData() {
        val dummyItems = listOf(
            HomeItem("1", "Шоколадный торт \"Мечта\"", "Насыщенный вкус, влажные коржи", "https://picsum.photos/seed/cake1/200/200", "Готов к заказу"),
            HomeItem("2", "Клубничные капкейки", "С нежным кремом и свежей ягодой", "https://picsum.photos/seed/cupcake2/200/200", "15 мин назад"),
            HomeItem("3", "Макаруны Ассорти", "Фисташка, малина, шоколад", "https://picsum.photos/seed/macaron3/200/200", "Новинка!"),
            HomeItem("4", "Лимонный тарт", "С воздушной меренгой", "https://picsum.photos/seed/tart4/200/200", "Сезонное"),
            HomeItem("5", "Эклер \"Классика\"", "С заварным кремом и глазурью", "https://picsum.photos/seed/eclair5/200/200", "Хит продаж"),
            HomeItem("6", "Чизкейк \"Нью-Йорк\"", "Классический рецепт", "https://picsum.photos/seed/cheesecake6/200/200", "Сегодня скидка!")
        )
        homeAdapter = HomeAdapter(dummyItems)
        binding.recyclerView.adapter = homeAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
