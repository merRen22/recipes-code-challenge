# Code Challenge

This is a technical test build for consuming an endpoint and displaying the data as well as saving the data to a local database

![alt text](https://raw.githubusercontent.com/merRen22/recipes-code-challenge/main/showcase/1.jpg)
![alt text](https://raw.githubusercontent.com/merRen22/recipes-code-challenge/main/showcase/2.jpg)

### Libraries used

- Navigation (Fragment transitions)
- View Binding (Bind views)
- ViewModel (Store and manage UI-related data)
- LiveData (Observable data)
- Kotlin Coroutine (Light-weight threads)
- Hilt (Dependency Injection for Android)
- Retrofit (HTTP client)
- Multi Module App
- Jetpack Compose
- Room Database

## Modules

The project contains the following modules:

- App ( Initial module with the main activity of the project. Because single activity was used on the project )
- Base ( Contains the navigation between the UI modules and generic classes )
- Data ( Used for making request to the API and the the local BD)
- Model ( Declares all the entities used in the project )
- Home ( Home screen to see items )
- Detail ( Detail screen leads to maps page )

## Architecture

This app was build using MVVM and following the guidelines explain [here](https://developer.android.com/jetpack/docs/guide). This app also makes use of the pattern single activity.

## Maps api key

In order to use the ```LocationScreen``` you need to add your own maps api key in the manifest as seen below 👇:
```xml
        <meta-data android:name="com.google.android.geo.API_KEY"
            android:value="google_api_key"/>
```
