package com.example.bingeboard.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bingeboard.R
import com.example.bingeboard.databinding.FragmentMoviesBinding
import com.example.bingeboard.databinding.FragmentWatchLaterBinding

class WatchLaterFragment : Fragment() {

    private lateinit var binding: FragmentWatchLaterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWatchLaterBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {
        private const val TAG = "WatchLaterFragment"
    }

}