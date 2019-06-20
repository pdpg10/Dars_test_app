package com.example.dars_test_app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.dars_test_app.model.Question

class QuestionAdapter(
    fm: FragmentManager,
    private val questionList: List<Question>
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val q = questionList[position]
        return QuestionFragment.create(q)
    }

    override fun getCount() = questionList.size

}
