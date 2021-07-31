package net.smartgekko.recyclerdiary.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import net.smartgekko.recyclerdiary.R
import net.smartgekko.recyclerdiary.databinding.ActivityMainBinding
import net.smartgekko.recyclerdiary.utilites.MyApplication
import net.smartgekko.recyclerdiary.utilites.SharedPreference
import net.smartgekko.recyclerdiary.views.fragments.CalendarFragment
import net.smartgekko.recyclerdiary.views.fragments.HomeFragment
import net.smartgekko.recyclerdiary.views.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreference: SharedPreference
    private lateinit var binding: ActivityMainBinding
    val myapp = MyApplication(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myapp.create()
        sharedPreference = SharedPreference(MyApplication.getAppContext())

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentsContainer.id, HomeFragment())
                .commitAllowingStateLoss()
        }
        val bottomNavigation = binding.bottomNavigation
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    true
                }
                R.id.calendar -> {
                    showFragment(CalendarFragment.newInstance())
                    true
                }
                R.id.settings -> {
                    showFragment(SettingsFragment.newInstance())
                    true
                }
                else -> true
            }
        }
    }

    private fun showFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentsContainer.id, fragment)
            .commitAllowingStateLoss()
    }
}