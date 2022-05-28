package com.C22PS320.Akrab.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.C22PS320.Akrab.*
import com.C22PS320.Akrab.databinding.ActivityMainBinding
import com.C22PS320.Akrab.preferences.SettingPreferences
import com.C22PS320.Akrab.preferences.ViewModelFactory
import com.C22PS320.Akrab.ui.kelas.ClassActivity
import com.etebarian.meowbottomnavigation.MeowBottomNavigation


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        supportActionBar?.hide()

//        val pref = SettingPreferences.getInstance(dataStore)
//        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref,this)).get(
//            MainViewModel::class.java
//        )
//        mainViewModel.getName().observe(this) {
//            binding.tvUsername.text = it
//        }
//        binding.btnKelashuruf.setOnClickListener {
//            val i = Intent(this, ClassActivity::class.java)
//            i.putExtra("class","huruf")
//            startActivity(i)
//        }
//        binding.btnKelasangka.setOnClickListener {
//            val i = Intent(this, ClassActivity::class.java)
//            i.putExtra("class","angka")
//            startActivity(i)
//        }
        addFragment(HomeFragment.newInstance())
        binding.bottomNavigation.show(0)
        binding.bottomNavigation.add(MeowBottomNavigation.Model(0, R.drawable.ic_baseline_home))
        binding.bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_baseline_library_books_24))
        binding.bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_baseline_subject_24))
        binding.bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_baseline_account_circle))

        binding.bottomNavigation.setOnClickMenuListener {
            when(it.id){
                0 -> {
                    replaceFragment(HomeFragment.newInstance())
                }
                1 -> {
                    replaceFragment(ModulFragment.newInstance())
                }
                2 -> {
                    replaceFragment(NewsFragment.newInstance())
                }
                3 -> {
                    replaceFragment(ProfileFragment.newInstance())
                }else ->{
                replaceFragment(HomeFragment.newInstance())
            }
            }
        }

    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.FragmentContainer,fragment).addToBackStack(Fragment::class.java.simpleName).commit()

    }
    private fun addFragment(fragment: Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        val commit = fragmentTransition.replace(R.id.FragmentContainer, fragment)
            .addToBackStack(Fragment::class.java.simpleName).commit()

    }
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }
}