package com.example.protocolsm2ble.presentation

import com.example.protocolsm2ble.domain.variables.BluetoothDevice

data class BluetoothUiState(
    val scannedDevices: List<BluetoothDevice> = emptyList(),
    val pairedDevices: List<BluetoothDevice> = emptyList(),
)
