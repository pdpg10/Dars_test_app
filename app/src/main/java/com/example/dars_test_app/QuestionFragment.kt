package com.example.dars_test_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.example.dars_test_app.model.Question
import kotlinx.android.synthetic.main.fragment_question.*


private const val ARG_PARAM_QUESTION = "ARG_PARAM_QUESTION"

class QuestionFragment : Fragment(), CompoundButton.OnCheckedChangeListener {

    private var question: Question? = null
    private var listener: OnAnswerCheckedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            question = it.getParcelable(ARG_PARAM_QUESTION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val localQuestion = question ?: return
        tv_question.text = question?.question

        (0 until rg.childCount)
            .map { rg.getChildAt(it) }
            .forEach {
                if (it is RadioButton) {
                    val index = rg.indexOfChild(it)
                    it.text = localQuestion.answers[index].answer
                    it.tag = localQuestion.answers[index].isCorrect
                    it.setOnCheckedChangeListener(this)
                }
            }

    }

    override fun onCheckedChanged(
        buttonView: CompoundButton?,
        isChecked: Boolean
    ) {
        if (isChecked) {
            val tag = buttonView?.tag
            if (tag is Boolean) {
                listener?.onAnswerChecked(tag)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAnswerCheckedListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnAnswerCheckedListener {
        fun onAnswerChecked(isCorrect: Boolean)
    }

    companion object {
        fun create(question: Question) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM_QUESTION, question)
                }
            }
    }
}
