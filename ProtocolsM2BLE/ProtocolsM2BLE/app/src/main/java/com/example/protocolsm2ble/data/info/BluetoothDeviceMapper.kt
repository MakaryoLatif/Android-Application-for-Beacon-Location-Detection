package com.example.protocolsm2ble.data.info

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.example.protocolsm2ble.domain.variables.BluetoothDeviceDomain
import kotlin.math.pow

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(rssi: Int): BluetoothDeviceDomain {
    val distance = calculateDistance(rssi)
    return BluetoothDeviceDomain(
        name = name,
        address = address,
        rssi = rssi,
        distance = distance
    )
}
fun calculateDistance(rssi: Int): Double {
    // Constants for LDP model
    val txPower = -59 // Reference RSSI at 1 meter distance
    val n = 2.0 // Path loss exponent, typically ranges from 2 to 4

    val ratio = rssi * 1.0 / txPower
    if (ratio < 1.0) {
        return ratio.pow(10.0)
    } else {
        return (0.89976 * ratio.pow(7.7095) + 0.111)
    }
}