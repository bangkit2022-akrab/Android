package com.C22PS320.Akrab.ui.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.C22PS320.Akrab.*
import com.C22PS320.Akrab.databinding.ActivityMainBinding
import com.C22PS320.Akrab.preferences.SettingPreferences
import com.C22PS320.Akrab.preferences.ViewModelFactory
import com.C22PS320.Akrab.ui.main.home.HomeFragment
import com.C22PS320.Akrab.ui.main.modul.ModulFragment
import com.C22PS320.Akrab.ui.main.profile.ProfileFragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            MainViewModel::class.java
        )
        lifecycleScope.launch {
            val token = mainViewModel.getToken()
            val name = mainViewModel.getName()
            mainViewModel.getModule(token)
                val fragment = HomeFragment.newInstance()
                val mBundle = Bundle()
                mBundle.putString(HomeFragment.EXTRA_NAME, name)
                fragment.arguments = mBundle
                replaceFragment(fragment)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        binding.bottomNavigation.show(0)
        binding.bottomNavigation.add(MeowBottomNavigation.Model(0, R.drawable.ic_baseline_home))
        binding.bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_baseline_menu_book))
        binding.bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_baseline_account_circle))

        binding.bottomNavigation.setOnClickMenuListener {
            when(it.id){
                0 -> {
                    mainViewModel.getUserName().observe(this) { it2 ->
                        val fragment = HomeFragment.newInstance()
                        val mBundle = Bundle()
                        mBundle.putString(HomeFragment.EXTRA_NAME, it2)
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
                2 -> {
                        val fragment = ProfileFragment.newInstance()
                        val mBundle = Bundle()
                    mainViewModel.getUserName().observe(this) { name ->
                        mainViewModel.getUserEmail().observe(this) { email ->
                            mBundle.putString(ProfileFragment.EXTRA_NAME, name)
                            mBundle.putString(
                                ProfileFragment.EXTRA_EMAIL,
                               email
                            )
                        }
                    }
                        fragment.arguments = mBundle
                        replaceFragment(fragment)

                }else ->{
                replaceFragment(HomeFragment.newInstance())
            }
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        binding.bottomNavigation
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.FragmentContainer,fragment).addToBackStack(Fragment::class.java.simpleName).commit()

    }

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    }
}
