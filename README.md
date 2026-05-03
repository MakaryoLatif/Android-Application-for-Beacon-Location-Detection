# Android BLE Beacon Discovery & Proximity Tracker

## Overview
This is a native Android application developed in Kotlin designed to scan, discover, and estimate the physical distance of nearby Bluetooth Low Energy (BLE) beacons. 

Built with scalability and maintainability in mind, the project strictly adheres to modern Android development practices (Clean Architecture) while implementing real-world networking physics to translate raw radio frequency signals into human-readable distance metrics.

## Software Architecture & Tech Stack
* **Language:** Kotlin
* **User Interface:** Jetpack Compose (Declarative UI)
* **Architecture Pattern:** MVVM (Model-View-ViewModel) with unidirectional data flow via `BluetoothUiState`.
* **Layered Design:** Strict separation of `data`, `domain`, and `presentation` layers to decouple hardware interfacing from the UI.
* **Dependency Injection:** Configured via `AppModule` to inject Bluetooth controllers and hardware receivers seamlessly.
* **Hardware Abstraction:** Wraps native Android Bluetooth APIs inside an `AndroidBluetoothController` to keep domain logic clean and testable.

## Hardware Integration & Physics Engine
Beyond simple device discovery, this application processes raw BLE broadcast packets to estimate physical proximity using standard RF path-loss models.

### 1. RSSI Processing
The app continuously monitors the **RSSI (Received Signal Strength Indicator)** of nearby beacons. Measured in dBm (decibel-milliwatts), this negative value indicates the raw signal strength hitting the Android device's Bluetooth antenna.

### 2. Distance Estimation Algorithm
To translate raw RSSI into an estimated distance in meters, the application utilizes the log-distance path-loss model:

`d = 10 ^ ((TxPower - RSSI) / (10 * n))`

* **TxPower:** The baseline calibration RSSI value (typically around -59 dBm) representing the expected signal strength at exactly 1 meter away.
* **n (Environmental Factor):** A constant (usually between 2.0 and 4.0) that accounts for signal degradation and absorption caused by physical obstacles and multipath fading in the real world.

### 3. Signal Smoothing
Because raw 2.4 GHz RF signals are highly volatile and prone to bouncing off walls or being absorbed by human bodies, the application relies on smoothing algorithms (such as moving averages) to filter out noise. This prevents the UI from "jumping" erratically and provides a stable, steady distance estimate to the user.

## 🚀 Setup & Building
1. Clone the repository.
2. Open the project in Android Studio.
3. **Hardware Required:** Ensure you have a physical Android device for testing. Native Bluetooth hardware APIs (scanning, RSSI reading) do not function fully on standard Android emulators.
4. Grant required Bluetooth and Location permissions upon launch.
5. Build and run using the included Gradle wrapper.
