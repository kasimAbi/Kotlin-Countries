# Countries
## Title
The name of this project is Countries. It describes the whole project in one sentence, helping people understand the main goal and aim of the project.

## Project Description
The following application is a small project to select countries, save them, and remove them. Saved countries are marked with a filled star. There is also a detail page where the selected country is visible in more detail, and a redirect to a detail page in the web browser is possible. Additionally, the flag of the corresponding country is displayed on the detail page. The app contains a bottom navigation bar, allowing users to switch between the homepage and the saved page. Only the saved countries are displayed on the saved page.

The app was created using the Kotlin programming language and the Android Studio IDE.

Some points that need improvement:
- Bottom Navigation: Adjust the colors of the icons when selected.
- Bottom Navigation: Adjust the bottom margin.
- Back Button: The parent activity of DetailActivity is MainActivity, so clicking "back" navigates to MainActivity.
- Detail Page: Center the title text.

## Used Libraries
- implementation("com.squareup.okhttp3:okhttp:4.9.3"): For sending and receiving network requests.
- implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2"): Basic support for coroutines in Kotlin, which are used for asynchronous programming.
- implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2"): Extends the basic coroutine functions with specific support for Android, including dispatchers and other tools.
- implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0"): Allows easy conversion of Kotlin objects to JSON and vice versa, useful for working with APIs and data transfer.
- implementation("io.coil-kt:coil:2.1.0"): Coil (Coroutine Image Loader) is an image loading library for Android, specifically designed for Kotlin and coroutines. It enables efficient loading, caching, and displaying of images in Android applications.
- implementation("io.coil-kt:coil-svg:2.1.0"): This extension of Coil adds support for loading and displaying SVG images.

## How to install and run the project
[Install Android Studio IDE. Click here for instructions.](https://developer.android.com/studio/install) </br></br>
[You also need to install an emulator to run the app on your PC. Click here for instructions.](https://developer.android.com/studio/run/emulator) </br></br>
Then copy the repository to a directory on your PC. </br></br>
Finally, open the project directory with the Android Studio IDE and run the project.

## How Does the App Work
The app is simply programmed.
- You can select one or more countries by clicking the appropriate star. The selected countries are marked with a filled star. You can also deselect countries.
  - The countries are stored in a singleton object and are loaded from an API.
  - Each country has a boolean variable saved.
- You can navigate from the bottom navigation bar to a page where only selected countries are visible.
- If you click on the name of each country, you will be navigated to the detail page with the details of the selected country.
- On the detail page, there is a button you can click to open the browser with more information about the selected country.
