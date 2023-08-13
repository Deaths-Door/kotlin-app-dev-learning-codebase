package com.example.calculator30.fragments.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.calculator30.R
import com.example.calculator30.databinding.FragmentCalculatorBinding
import com.example.calculator30.databinding.FragmentConvertorBinding

class Calculator : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View = inflater.inflate(R.layout.fragment_calculator,container,false)
}