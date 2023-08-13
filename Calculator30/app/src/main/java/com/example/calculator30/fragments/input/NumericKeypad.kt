package com.example.calculator30.fragments.input

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.calculator30.R

class NumericKeypad : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        =  inflater.inflate(R.layout.fragment_numeric_keypad, container, false)
}