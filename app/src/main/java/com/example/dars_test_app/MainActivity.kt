package com.example.dars_test_app

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.dars_test_app.model.Question
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
    , QuestionFragment.OnAnswerCheckedListener {
    private var checkedAnswers: BooleanArray = BooleanArray(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViewPager()
        btn_check.setOnClickListener {
            val items = checkedAnswers.filter { it }.size
            val msg = "Correct answers ${items}/${checkedAnswers.size - items}"
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpViewPager() {
        Log.d("setUpViewPager", "size of list:")
        val listQuestion = loadQuestions()

        checkedAnswers = BooleanArray(listQuestion.size)

        Log.d("setUpViewPager", "size of list:${listQuestion.size}")
        view_pager.adapter = QuestionAdapter(supportFragmentManager, listQuestion)

        view_pager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == (listQuestion.size - 1)) {
                    btn_check.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun loadQuestions(): List<Question> {
        val jsonString = loadFromAsset()
        if (jsonString.isEmpty()) return listOf()
        val gson = Gson()
        Log.d("loadQuestions", "loadQuestions $jsonString")
        val questions: Array<Question> = gson.fromJson(jsonString, Array<Question>::class.java)
        return listOf(*questions)
    }

    private fun loadFromAsset(): String {
        //todo how to and why use @use method https://www.baeldung.com/kotlin-try-with-resources
        val jsonData = assets.open(FILE_NAME).bufferedReader()
            .use { it.readText() }
        return jsonData
    }

    override fun onAnswerChecked(isCorrect: Boolean) {
        checkedAnswers[view_pager.currentItem] = isCorrect
    }
}
