package com.dongminpark.foodmarketandroid.Screens


import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.foodmarketandroid.Button.BackButton
import com.dongminpark.foodmarketandroid.R
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView

@Composable
fun FoodBankDetailScreen(navController: NavController) {
    Column {
        TopAppBar(
            navigationIcon = {
                BackButton(navController = navController)
            },
            title = {
                Text(
                    text = "지점 명",
                ) // 텍스트 API로 받아와서 할 예정
            },
            elevation = 4.dp
        )
        TmapView()
    }
}

@Composable
fun fbds(){
    var navController = rememberNavController()
    Column {
        TopAppBar(
            navigationIcon = {
                BackButton(navController = navController)
            },
            title = {
                Text(
                    text = "지점 명",
                ) // 텍스트 API로 받아와서 할 예정
            },
            elevation = 4.dp
        )
        TmapView()
    }
}

@Preview
@Composable
fun fbdsPreview(){
    fbds()
}


@Composable
fun TmapView() {
    val context = LocalContext.current
    val tMapView = remember { TMapView(context) }

    AndroidView(factory = { tMapView })

    val centerLatitude = 37.44845494879633
    val centerLongitude = 126.6494939089789

    LaunchedEffect(tMapView) {
        tMapView.setSKTMapApiKey("mpqOluGuKn4C1DszIeY9Z33DCyl4BhPR6DGHaQT9")
        addMarkerToTmapView(tMapView, 37.44650724756766, 126.63704946161081)
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN)
        tMapView.setZoomLevel(13)
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD)
        tMapView.setLocationPoint(centerLongitude, centerLatitude) // 기준 위치 설정
        tMapView.setIconVisibility(true) // 현재 위치 아이콘 표시
        tMapView.setCenterPoint(centerLongitude, centerLatitude) // 화면 중앙 위치 설정
    }
}

fun addMarkerToTmapView(tmapView: TMapView, latitude: Double, longitude: Double) {
    val context = tmapView.context
    val marker = TMapMarkerItem()

    marker.tMapPoint = TMapPoint(latitude, longitude)
    marker.icon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_foreground) // 마커 이미지 설정
    marker.setCalloutTitle("마커") // 마커 타이틀 설정
    marker.setCalloutSubTitle("설명") // 마커 부제목 설정

    tmapView.addMarkerItem("marker_id_$latitude$longitude", marker)
}