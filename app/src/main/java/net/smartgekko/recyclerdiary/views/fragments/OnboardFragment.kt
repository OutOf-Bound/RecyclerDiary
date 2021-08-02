package net.smartgekko.recyclerdiary.views.fragments

import android.graphics.Typeface.*
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import net.smartgekko.recyclerdiary.R
import net.smartgekko.recyclerdiary.utilites.MyApplication
import net.smartgekko.recyclerdiary.utilites.SharedPreference


class OnboardFragment : Fragment() {
    private val sharedPreference: SharedPreference = SharedPreference(MyApplication.getAppContext())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_onboard, container, false)
        val onboardText = view.findViewById<TextView>(R.id.onboardText)
        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        checkBox.setOnClickListener {
            when (checkBox.isChecked) {
                true -> {
                    sharedPreference.saveBoolean(
                        "needOnboard",
                        false
                    )
                }
                else -> {
                    sharedPreference.saveBoolean(
                        "needOnboard",
                        true
                    )
                }
            }
        }
        val loadingLayout = view.findViewById<ConstraintLayout>(R.id.loadingLayout)
        val closeLayout = view.findViewById<ConstraintLayout>(R.id.closeLayout)
        closeLayout.setOnClickListener {
            closeLayout.elevation = 1.0f
            loadingLayout.visibility = View.VISIBLE
            activity?.supportFragmentManager?.beginTransaction()!!
                .replace(R.id.fragmentsContainer, HomeFragment())
                .commitAllowingStateLoss()
        }
        onboardText.text = prepareText()
        onboardText.isClickable = true
        onboardText.movementMethod = LinkMovementMethod.getInstance()
        return view
    }

    override fun onStart() {
        super.onStart()
    }

    companion object {
        @JvmStatic
        fun newInstance() = OnboardFragment().apply {

        }
    }

    private fun prepareText(): SpannableStringBuilder {
        val sb: SpannableStringBuilder = SpannableStringBuilder()
        val text_title: SpannableString = SpannableString("WELCOME\n")
        val text_body: SpannableString = SpannableString(
            "The very first thing users see when downloading an app these days is an onboarding screen.\n" +
                    " An onboarding screen is like a walkthrough, aimed to introduce what an app does to a user and of course\n" +
                    "how to use it. Thta’s the simplest way of describing it. Designing it however is a totally different thing.\n" +
                    "An onboarding screen needs to be designed\n" +
                    "in the most simple, welcoming and user-friendly way possible.\n" + " Onboarding screens like empty state pages created to inform and educate users.\n" +
                    " Not every app needs an onboarding screen although\n" +
                    " I think onboarding screens save users from the frustration of having to\n" +
                    " figure out on their own that new app they are trying out.\n"
        )
        val text_ref: SpannableString =
            SpannableString("Also, Let’s connect on\n\n Twitter,  Linkedin,  Github,  Facebook")

        sb.append(text_title)
        sb.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.color6)),
            0,
            1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        sb.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.color2)),
            1,
            2,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        sb.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.color3)),
            2,
            3,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        sb.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.color5)),
            3,
            4,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        sb.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.color4)),
            4,
            5,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        sb.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.color1)),
            5,
            6,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        sb.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.color0)),
            6,
            7,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        sb.setSpan(StyleSpan(BOLD), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        sb.setSpan(RelativeSizeSpan(2f), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        text_body.setSpan(
            StyleSpan(ITALIC),
            407,
            text_body.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text_body.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.color0)),
            0,
            3,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text_body.setSpan(RelativeSizeSpan(1.5f), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        text_body.setSpan(
            BackgroundColorSpan(resources.getColor(R.color.color2)),
            55,
            82,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text_body.setSpan(StyleSpan(NORMAL), 251, 406, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        text_body.setSpan(StyleSpan(BOLD), 251, 406, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        text_body.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.color1)),
            407,
            text_body.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text_body.setSpan(
            BackgroundColorSpan(resources.getColor(R.color.color6)),
            500,
            555,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text_body.setSpan(
            RelativeSizeSpan(1.1f),
            0,
            text_body.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        sb.append(text_body)
        text_ref.setSpan(RelativeSizeSpan(1.3f), 0, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        text_ref.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.white)),
            0,
            22,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text_ref.setSpan(
            BackgroundColorSpan(resources.getColor(R.color.black)),
            0,
            22,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text_ref.setSpan(
            RelativeSizeSpan(1.1f),
            0,
            text_ref.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text_ref.setSpan(StyleSpan(ITALIC), 22, text_ref.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Toast.makeText(context, "Linkedin reference clicked!", Toast.LENGTH_LONG).show()
            }
        }
        text_ref.setSpan(clickableSpan, 34, 44, 0)
        text_ref.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.color5)),
            34,
            44,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text_ref.setSpan(UnderlineSpan(), 34, 44, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        sb.append(text_ref)

        return sb
    }
}