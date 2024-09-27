# Face Detection and Tagging Android Application

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the App](#running-the-app)
- [Usage](#usage)
- [Technologies Used](#technologies-used)

---

## Overview

The **Face Detection and Tagging Android Application** Android app designed to detect faces within images from the user's gallery and allow tagging of detected faces with names.

---

## Features

- **Permission Handling**: Seamlessly requests and manages necessary permissions to access the device's gallery.
- **Gallery Display**: Shows images from the user's gallery that contain detected faces.
- **Face Detection**: Utilizes **Mediapipe** for efficient and accurate face detection within images.
- **Tagging Faces**: Allows users to tag detected faces with names directly on the image.

---

## Getting Started

### Prerequisites

- **Android Studio**
- **Android SDK**
- **Kotlin**: Version 1.8.10 or later.
- **Gradle**: Version compatible with the Android Gradle Plugin used.

### Installation

1. **Open in Android Studio**

   - Launch Android Studio.
   - Click on **"Open an Existing Project"**.
   - Navigate to the cloned repository folder and select it.

2. **Sync Gradle**

   - Allow Android Studio to sync and download all necessary dependencies.
   - Ensure there are no sync errors before proceeding.

### Running the App

1. **Connect an Android Device or Start an Emulator**

   - Ensure that the device has **READ_EXTERNAL_STORAGE** permission enabled.
   - For physical devices, enable **Developer Options** and **USB Debugging**.

2. **Build and Run**

   - Click on the **Run** button in Android Studio.
   - Select the target device or emulator.
   - The app should launch, prompting for necessary permissions.

---

## Usage

1. **Grant Permissions**

   - Upon launching, the app will request permission to access your device's gallery.
   - Grant the **READ_EXTERNAL_STORAGE** permission to allow the app to scan and process images.

2. **View Gallery**

   - The app displays a grid of images that contain detected faces.

3. **Detect and Tag Faces**

   - Tap on an image to navigate to the face detection screen.
   - Detected faces are highlighted with red rectangles.
   - Tag names appear above each bounding box.
   - Click on a tag name or bounding box to add or edit the tag.

4. **Manage Tags**

   - Add new tags by entering names when prompted.
   - Tags are saved and associated with the respective face detections.

---

## Technologies Used

- **Programming Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Dependency Injection**: Dagger Hilt
- **Asynchronous Processing**: Kotlin Coroutines and Flow
- **Face Detection**: [Mediapipe](https://mediapipe.dev/)
