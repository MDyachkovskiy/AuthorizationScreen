object Versions {

    //Kotlin
    const val core_ktx = "1.12.0"

    //AndroidX
    const val appcompat = "1.6.1"
    const val compose_runtime = "1.5.4"

    //Design
    const val material = "1.10.0"
    const val constraint_layout = "2.1.4"

    //Koin
    const val koin_android = "3.4.2"
    const val koin_core = "3.4.2"
    const val koin_androidx = "3.4.0"
    const val koin_navigation = "3.4.0"

    //Retrofit
    const val retrofit_main = "2.9.0"
    const val gson = "2.9.0"
    const val retrofit_gson_converter = "2.9.0"

    //Navigation
    const val fragment_ktx = "2.7.5"
    const val ui_ktx = "2.7.5"
}
object Kotlin {
    const val core = "androidx.core:core-ktx:${Versions.core_ktx}"
}
object AndroidX {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val compose_runtime = "androidx.compose.runtime:runtime-android:${Versions.compose_runtime}"
}
object Design {
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
}
object Koin {
    const val android = "io.insert-koin:koin-android:${Versions.koin_android}"
    const val core = "io.insert-koin:koin-core:${Versions.koin_core}"
    const val navigation = "io.insert-koin:koin-androidx-navigation:${Versions.koin_navigation}"
    const val androidx = "io.insert-koin:koin-androidx-compose:${Versions.koin_androidx}"
}
object Retrofit {
    const val main = "com.squareup.retrofit2:retrofit:${Versions.retrofit_main}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val gson_convertor = "com.squareup.retrofit2:converter-gson:${Versions.retrofit_gson_converter}"
}
object Navigation {
    const val fragment_ktx = "androidx.navigation:navigation-fragment-ktx:${Versions.fragment_ktx}"
    const val ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.fragment_ktx}"
}
