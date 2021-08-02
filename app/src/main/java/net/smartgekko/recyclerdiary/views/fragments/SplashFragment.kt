package net.smartgekko.recyclerdiary.views.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.HandlerCompat.postDelayed
import net.smartgekko.recyclerdiary.R
import net.smartgekko.recyclerdiary.utilites.MyApplication
import net.smartgekko.recyclerdiary.utilites.SPLASH_DISPLAY_TIMEOUT
import net.smartgekko.recyclerdiary.utilites.SharedPreference


class SplashFragment : Fragment() {
    private val sharedPreference: SharedPreference = SharedPreference(MyApplication.getAppContext())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_splash, container, false)

        return view
    }

    override fun onStart() {
        super.onStart()
        var nextFragment: Fragment
        if (sharedPreference.getValueBoolean("needOnboard") == true) {
            nextFragment = OnboardFragment.newInstance()
        } else {
            nextFragment = HomeFragment.newInstance()
        }
        Handler().postDelayed({
            activity?.supportFragmentManager?.beginTransaction()!!
                .replace(R.id.fragmentsContainer, nextFragment)
                .commitAllowingStateLoss()
        }, SPLASH_DISPLAY_TIMEOUT)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SplashFragment().apply {

            }
    }
}