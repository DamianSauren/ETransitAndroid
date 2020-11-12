package com.example.etransportandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.etransportandroid.R

class PrivateBookingFragment: Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_private_booking, container, false)

        return inflate
    }

}