package com.example.lab2

import android.os.Bundle
// AppCompatActivity больше не нужен напрямую
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab2.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity() { // ИЗМЕНЕНО

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Вызов super.onCreate() из BaseActivity выполнит логирование
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)

        setupRecyclerView()
        loadDummyData()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        homeAdapter = HomeAdapter(emptyList()) // Классы HomeAdapter и HomeItem должны быть в пакете com.example.lab2
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
}
