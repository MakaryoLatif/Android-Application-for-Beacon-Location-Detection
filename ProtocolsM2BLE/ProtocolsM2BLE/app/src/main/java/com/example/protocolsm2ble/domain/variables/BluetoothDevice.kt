package com.example.protocolsm2ble.domain.variables

typealias BluetoothDeviceDomain = BluetoothDevice

data class BluetoothDevice(
    val name: String?,
    val address: String,
    var rssi: Int,
    var distance: Double // Distance estimation

)
