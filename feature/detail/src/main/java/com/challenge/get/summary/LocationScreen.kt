package com.challenge.get.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun LocationScreen(
    detailViewModel: DetailViewModel
) {
    val recipe = detailViewModel.recipe.observeAsState()
    recipe.value?.let {
        val coordinates = LatLng(it.origin.first, it.origin.second)

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(coordinates, 5f)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = coordinates),
                title = it.name,
                snippet = it.origin_name
            )
        }
    } ?: ProgressIndicator()
}