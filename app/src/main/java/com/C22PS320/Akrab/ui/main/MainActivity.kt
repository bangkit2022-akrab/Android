package com.C22PS320.Akrab.ui.main

import android.content.Context
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
import com.C22PS320.Akrab.ui.main.home.HomeFragment
import com.C22PS320.Akrab.ui.main.modul.ModulFragment
import com.C22PS320.Akrab.ui.main.news.NewsFragment
import com.C22PS320.Akrab.ui.main.profile.ProfileFragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref,this)).get(
            MainViewModel::class.java
        )
        mainViewModel.getToken().observe(this) { token ->
            mainViewModel.getModule(token)
        }
        mainViewModel.getName().observe(this) {
            val fragment = HomeFragment.newInstance()
            val mBundle = Bundle()
            mBundle.putString(HomeFragment.EXTRA_NAME, it)
            fragment.arguments = mBundle
            replaceFragment(fragment)
        }
        binding.bottomNavigation.show(0)
        binding.bottomNavigation.add(MeowBottomNavigation.Model(0, R.drawable.ic_baseline_home))
        binding.bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_baseline_library_books_24))
//        binding.bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_baseline_subject_24))
        binding.bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_baseline_account_circle))

        binding.bottomNavigation.setOnClickMenuListener {
            when(it.id){
                0 -> {
                    mainViewModel.getName().observe(this) {
                        val fragment = HomeFragment.newInstance()
                        val mBundle = Bundle()
                        mBundle.putString(HomeFragment.EXTRA_NAME, it)
                        fragment.arguments = mBundle
                        replaceFragment(fragment)
                    }
                }
                1 -> {
                    val fragment = ModulFragment.newInstance()
                        val mBundle = Bundle()
                        mBundle.putParcelable(ModulFragment.TOKEN, mainViewModel.module.value)
                        fragment.arguments = mBundle
                        replaceFragment(fragment)
                }
//                2 -> {
//                    replaceFragment(NewsFragment.newInstance())
//                }
                2 -> {
                    mainViewModel.getName().observe(this) {
                        val fragment = ProfileFragment.newInstance()
                        val mBundle = Bundle()
                        mBundle.putString(ProfileFragment.EXTRA_NAME, it)
                        fragment.arguments = mBundle
                        replaceFragment(fragment)
                    }

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
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }
}
