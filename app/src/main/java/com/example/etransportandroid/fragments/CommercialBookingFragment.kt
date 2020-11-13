package com.example.etransportandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.etransportandroid.R
import com.example.etransportandroid.data.CommercialOrder
import com.example.etransportandroid.data.Database
import com.google.firebase.auth.FirebaseAuth

class CommercialBookingFragment: Fragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_commercial_booking, container, false)

        mAuth = FirebaseAuth.getInstance()

        val button = inflate.findViewById<Button>(R.id.submit_button)
        button.setOnClickListener {
            Database().writeNewOrder(mAuth.currentUser?.uid.toString(), CommercialOrder(
                    itemDescription = inflate.findViewById<EditText>(R.id.item_description_edittext).text.toString(),
                    weight = inflate.findViewById<EditText>(R.id.weight_edittext).text.toString(),
                    PickUpDate = inflate.findViewById<EditText>(R.id.pickup_date_edittext).text.toString(),
                    hazards = inflate.findViewById<EditText>(R.id.hazards_edittext).text.toString(),
                    timeFrame = inflate.findViewById<EditText>(R.id.timeframe_edittext).text.toString().toInt(),
                    bookingDate = inflate.findViewById<EditText>(R.id.pickup_date_edittext).text.toString(),
                    dimensions = CommercialOrder.Dimensions (
                            height = inflate.findViewById<EditText>(R.id.height_edittext).text.toString(),
                            length = inflate.findViewById<EditText>(R.id.lenght_edittext).text.toString(),
                            depth = inflate.findViewById<EditText>(R.id.depth_edittext).text.toString()
                    ),
                    locations = CommercialOrder.Locations(
                            to = inflate.findViewById<EditText>(R.id.location_to_edittext).text.toString(),
                            from = inflate.findViewById<EditText>(R.id.location_from_textview).text.toString()
                    )
            ))
        }

        return inflate
    }
}