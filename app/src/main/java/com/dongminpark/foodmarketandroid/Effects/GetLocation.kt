package com.dongminpark.foodmarketandroid.Effects

fun getLatitude(): Double = 37.449406297743074
fun getLongitude() : Double = 126.65434902346904

/*
@Composable
fun GetLocationScreen() {
    val context = LocalContext.current

    Column {
        Button(onClick = { requestLocationPermission(context) }) {
            Text("Get Location")
        }
    }
}

private fun requestLocationPermission(context: Context) {
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    } else {
        fetchLocation()
    }
}

private fun fetchLocation() {
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                // Use latitude and longitude values as needed
            }
        }
}

companion object {
    private const val LOCATION_PERMISSION_REQUEST_CODE = 123
}

 */