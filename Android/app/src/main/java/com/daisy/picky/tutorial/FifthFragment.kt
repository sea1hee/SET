package com.daisy.picky.tutorial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daisy.picky.R
import com.daisy.picky.databinding.FragmentFifthBinding
import com.daisy.picky.databinding.FragmentFirstBinding

class FifthFragment : Fragment() {
    private var binding: FragmentFifthBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFifthBinding.inflate(inflater, container, false)

        return binding!!.root
    }

}