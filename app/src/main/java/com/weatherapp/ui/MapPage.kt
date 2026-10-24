package com.weatherapp.ui

import android.content.pm.PackageManager
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.weatherapp.viewmodel.MainViewModel
import java.util.Locale

@Composable
fun MapPage(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val context = LocalContext.current

    // Verifica se a permissão de localização precisa foi concedida
    val hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Estado da câmera do mapa
    val camPosState = rememberCameraPositionState()

    // Marcador fixo: Recife
    val recife = remember {
        MarkerState(position = LatLng(-8.05, -34.9))
    }

    // Marcador fixo: Caruaru
    val caruaru = remember {
        MarkerState(position = LatLng(-8.27, -35.98))
    }

    // Marcador fixo: João Pessoa
    val joaoPessoa = remember {
        MarkerState(position = LatLng(-7.12, -34.84))
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = camPosState,

        // Habilita a localização do usuário quando há permissão
        properties = MapProperties(
            isMyLocationEnabled = hasLocationPermission
        ),

        // Exibe o botão para centralizar na localização atual
        uiSettings = MapUiSettings(
            myLocationButtonEnabled = true
        ),

        onMapClick = { coordenadas ->
            val lat = String.format(
                Locale.US,
                "%.2f",
                coordenadas.latitude
            )

            val lng = String.format(
                Locale.US,
                "%.2f",
                coordenadas.longitude
            )

            // Adiciona a cidade ao ViewModel
            viewModel.add(
                name = "Cidade@$lat:$lng",
                location = coordenadas
            )
        }
    ) {
        // Marcadores das cidades adicionadas pelo usuário
        viewModel.cities.forEach { city ->
            val location = city.location

            if (location != null) {
                val markerState = remember {
                    MarkerState(position = location)
                }

                Marker(
                    state = markerState,
                    title = city.name,
                    snippet = "$location"
                )
            }
        }

        // Marcador fixo: Recife
        Marker(
            state = recife,
            title = "Recife",
            snippet = "Marcador em Recife",
            icon = BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_BLUE
            )
        )

        // Marcador fixo: Caruaru
        Marker(
            state = caruaru,
            title = "Caruaru",
            snippet = "Marcador em Caruaru",
            icon = BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_GREEN
            )
        )

        // Marcador fixo: João Pessoa
        Marker(
            state = joaoPessoa,
            title = "João Pessoa",
            snippet = "Marcador em João Pessoa",
            icon = BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_RED
            )
        )
    }
}