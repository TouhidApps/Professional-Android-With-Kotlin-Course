package com.touhidapps.drivingtestquiz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.touhidapps.drivingtestquiz.databinding.FragmentScoreBinding

class ScoreFragment: Fragment() {

    private lateinit var binding : FragmentScoreBinding
    private var tempTotalQues: Int = 0
    private var tempTotalCorrect: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tempTotalQues = arguments?.getInt("TOTAL_QUES", 0) ?: 0
        tempTotalCorrect = arguments?.getInt("TAOTAL_CORRECT", 0) ?: 0

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTotalQuestions.text = "${tempTotalQues}"
        binding.tvCorrectlyAnswared.text = "${tempTotalCorrect}"

    }




}