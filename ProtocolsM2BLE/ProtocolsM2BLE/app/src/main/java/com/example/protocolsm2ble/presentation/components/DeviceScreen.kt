package com.example.protocolsm2ble.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.protocolsm2ble.domain.variables.BluetoothDevice
import com.example.protocolsm2ble.domain.variables.BluetoothDeviceDomain
import com.example.protocolsm2ble.presentation.BluetoothUiState
import com.example.protocolsm2ble.presentation.BluetoothViewModel

@Composable
fun DeviceScreen(
    state: BluetoothUiState,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit
) {
    val viewModel: BluetoothViewModel = hiltViewModel()
    val context = LocalContext.current
    val scannedDevices = state.scannedDevices
    val pairedDevices = state.pairedDevices
    val bluetoothController = viewModel.getBluetoothController()

    // Use remember to track scanned device for showing grid
    var scannedDevice by remember { mutableStateOf<BluetoothDeviceDomain?>(null) }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BluetoothDeviceList(
            pairedDevices = state.pairedDevices,
            scannedDevices = state.scannedDevices,
            onClick = {device ->
                scannedDevice = device
                viewModel.startScan()
                      },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        scannedDevice?.let { device ->
            Grid(
                scannedDevice = device,
                exitGrid = {
                    scannedDevice = null
                }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = onStartScan) {
                Text(text = "SCAN")
            }
            Button(onClick = onStopScan) {
                Text(text = "STOP")
            }
        }
    }
}

@Composable
fun BluetoothDeviceList(
    pairedDevices: List<BluetoothDevice>,
    scannedDevices: List<BluetoothDevice>,
    onClick: (BluetoothDevice) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Text(
                text = "Paired Devices",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(pairedDevices) { device ->
            Text(
                text = device.name ?: "[UNKNOWN DEVICE]",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(device) }
                    .padding(16.dp)
            )
        }

        item {
            Text(
                text = "Scanned Devices",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(scannedDevices) { device ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(device) }
                    .padding(16.dp)
            ) {
                Text(
                    text = device.name ?: "[UNKNOWN DEVICE]",
                    fontSize = 18.sp
                )
                Text(
                    text = "RSSI: ${device.rssi}, Distance: ${device.distance} meters",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }

}
@Composable
fun Grid(
    scannedDevice: BluetoothDeviceDomain,
    exitGrid: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Draw a line connecting two points representing your device and the scanned device
            drawGrid()
            drawDevicePosition(scannedDevice.distance)
        }

        // Button to exit the grid
        Button(
            onClick = exitGrid,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text(text = "Exit Grid")
        }
    }
}
private fun DrawScope.drawGrid() {
    val gridSize = 10
    val cellSize = size.minDimension / gridSize

    // Draw grid lines
    for (i in 0..gridSize) {
        val pos = i * cellSize
        drawLine(
            color = Color.LightGray,
            start = Offset(pos, 0f),
            end = Offset(pos, size.height)
        )
        drawLine(
            color = Color.LightGray,
            start = Offset(0f, pos),
            end = Offset(size.width, pos)
        )
    }

    // Draw user position at the center
    val center = Offset(size.width / 2, size.height / 2)
    drawCircle(
        color = Color.Blue,
        center = center,
        radius = cellSize / 2
    )
}

private fun DrawScope.drawDevicePosition(distance: Double) {
    // Map distance to grid position (assuming distance is in meters)
    val maxDistance = 10 // Maximum distance to consider
    val gridSize = 10
    val cellSize = size.minDimension / gridSize

    val positionFactor = distance / maxDistance
    val offset = cellSize * gridSize * positionFactor.toFloat() / 2

    val center = Offset(size.width / 2, size.height / 2)
    val devicePosition = center + Offset(offset, offset)

    drawCircle(
        color = Color.Red,
        center = devicePosition,
        radius = cellSize / 2
    )
}