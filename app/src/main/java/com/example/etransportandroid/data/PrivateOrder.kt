package com.example.etransportandroid.data

data class PrivateOrder (
        var itemDescription: String = "",
        var weight: String = "",
        var PickUpDate: String = "",
        var hazards: String = "",
        var truckId: String = "",
        var bookingDate: String = "",
        var dimensions: Dimensions,
        var locations: Locations
) {
    data class  Dimensions (
            var height: String = "",
            var length: String = "",
            var depth: String = ""
    )

    data class Locations (
            var to: String = "",
            var from: String = ""
    )
}