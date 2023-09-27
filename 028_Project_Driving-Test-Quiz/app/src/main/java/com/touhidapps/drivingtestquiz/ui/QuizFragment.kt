package com.touhidapps.drivingtestquiz.ui

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.touhidapps.drivingtestquiz.QuizModel
import com.touhidapps.drivingtestquiz.addFragment
import com.touhidapps.drivingtestquiz.databinding.FragmentQuizBinding
import java.io.InputStream

class QuizFragment: Fragment() {

    private lateinit var binding : FragmentQuizBinding

    private val quizs = arrayOf(
        QuizModel(
            id = 0,
            question = "কুলিং ফ্যানের কাজ কী?",
            imageUrl = "",
            answers = arrayOf(
                "ক। রেডিয়েটরের পানিকে ঠান্ডা করা",
                "খ। ইঞ্জিন অয়েলকে ঠান্ডা করা",
                "গ। ব্রেক অয়েলকে ঠান্ডা করা",
                "ঘ। ব্যাটারীকে ঠান্ডা করা"
            ),
            correctAnsIndex = 0
        ),
        QuizModel(
            id = 1,
            question = "ইঞ্জিন অতিরিক্ত গরম হওয়ার কারণ",
            imageUrl = "",
            answers = arrayOf(
                "ক) কুলিং ফ্যান কাজ না করলে",
                "খ) রেডিয়টরে পানি ও মবিল না থাকলে বা কম থাকলে",
                "গ) উপরের সবগুলি"),
            correctAnsIndex = 2
        ),
        QuizModel(
            id = 2,
            question = "এই চিহ্ন দ্বারা কি বুঝায়?",
            imageUrl = "no_cycle.png",
            answers = arrayOf(
                "ক) শুধুমাত্র মোরটসাইকেল চলাচলের জন্য",
                "খ) সাইকেল চলাচল নিষেধ",
                "গ) মোটরসাইকেল চলাচল নিষেধ",
                "ঘ) শুধুমাত্র মোটরসাইকেল চলাচলের জন্য"
            ),
            correctAnsIndex = 1
        )
    )
    private var tempCorrectAns = 0
    private var tempQuestionIndex = 0
    private var tempCorrectAnsCount = 0
    private var isTimeUp = false
    private var tempIsAnsClicked = false

    private val countDownTimer = object : CountDownTimer(15000, 1000) {
        override fun onTick(p0: Long) {
            val sec: Int = (p0 / 1000).toInt()
            binding.tvTimer.text = "00:%02d".format(sec)
            isTimeUp = false
        }

        override fun onFinish() {
            binding.tvTimer.text = "Time's up 🛑"
            isTimeUp = true
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setTheQuestion(tempQuestionIndex)



        binding.btnAns1.setOnClickListener {
            ansClickOperation(0, it)
        }
        binding.btnAns2.setOnClickListener {
            ansClickOperation(1, it)
        }
        binding.btnAns3.setOnClickListener {
            ansClickOperation(2, it)
        }
        binding.btnAns4.setOnClickListener {
            ansClickOperation(3, it)
        }

        binding.btnNext.setOnClickListener {

            tempQuestionIndex = tempQuestionIndex + 1


            if (tempQuestionIndex < quizs.count()) {
                setTheQuestion(tempQuestionIndex)
            } else {

                val bundle = Bundle().apply {
                    putInt("TOTAL_QUES", quizs.count())
                    putInt("TAOTAL_CORRECT", tempCorrectAnsCount)
                }


                addFragment(ScoreFragment(), requireActivity(), bundle)
            }


        }


    } // onViewCreated


    private fun ansClickOperation(ansIndex: Int, clickedView: View?) {

        if (tempIsAnsClicked) {
            return
        }

        if (isTimeUp) {
            Toast.makeText(activity, "Time's up", Toast.LENGTH_SHORT).show()
            return
        }

        val ansButtons = arrayOf(
            binding.btnAns1,
            binding.btnAns2,
            binding.btnAns3,
            binding.btnAns4
        )

        ansButtons.forEach {
            it.background.setTint(Color.GRAY)
        }


        if (ansIndex != -1) {

            tempIsAnsClicked = true
            countDownTimer.cancel()

            if (ansIndex == tempCorrectAns) { // Ans Correct

                tempCorrectAnsCount += 1

                clickedView?.background?.setTint(Color.BLUE)

            } else { // Ans Wrong

                ansButtons.forEachIndexed { index, materialButton ->

                    if (index == ansIndex) {
                        // clickedView?.background?.setTint(Color.RED)
                        materialButton.background.setTint(Color.RED)
                    }
                    if (index == tempCorrectAns) {
                        materialButton.background.setTint(Color.BLUE)
                    }

                }

            }
        }





    } // ansClickOperation

    private fun setTheQuestion(qIndex: Int) {

        tempIsAnsClicked = false

        ansClickOperation(-1, null)

        val q = quizs[qIndex]

        binding.tvQuestion.text = q.question


      //  binding.ivThumb.setImageResource(R.drawable.baseline_info_24)

        if (q.imageUrl.isEmpty()) {
            binding.ivThumb.visibility = View.GONE
        } else {
            binding.ivThumb.visibility = View.VISIBLE

            try {

                val inputStream: InputStream? = activity?.assets?.open("img/${q.imageUrl}")
                val d: Drawable? = Drawable.createFromStream(inputStream, null)
                binding.ivThumb.setImageDrawable(d)

            } catch (e: Exception) { e.printStackTrace() }

        }



        if (q.answers.count() > 0) {
            binding.btnAns1.visibility = View.VISIBLE
            binding.btnAns1.text = q.answers[0]
        } else {
            binding.btnAns1.visibility = View.GONE
        }
        if (q.answers.count() > 1) {
            binding.btnAns2.visibility = View.VISIBLE
            binding.btnAns2.text = q.answers[1]
        } else {
            binding.btnAns2.visibility = View.GONE
        }
        if (q.answers.count() > 2) {
            binding.btnAns3.visibility = View.VISIBLE
            binding.btnAns3.text = q.answers[2]
        } else {
            binding.btnAns3.visibility = View.GONE
        }
        if (q.answers.count() > 3) {
            binding.btnAns4.visibility = View.VISIBLE
            binding.btnAns4.text = q.answers[3]
        } else {
            binding.btnAns4.visibility = View.GONE
        }


        tempCorrectAns = q.correctAnsIndex

        countDownTimer.start()

    } // setTheQuestion




}