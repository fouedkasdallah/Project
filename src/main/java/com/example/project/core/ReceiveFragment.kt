package com.example.project.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project.R

class ReceiveFragment : Fragment() {
/*

    companion object {
        fun newInstance() = ReceiveFragment()
    }
*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.receivefragment, container, false)
    }
}