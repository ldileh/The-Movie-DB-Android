@Suppress("unused")
object BuildPlugins{
    const val android = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin.gradle}"
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
}

@Suppress("unused")
object Dependencies {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val androidSupport = "com.android.support:support-v4:${Versions.androidSupport}"
    const val lifeCycleJava = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifeCycleJava}"
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"

    const val KotlinCore = "androidx.core:core-ktx:${Versions.Kotlin.core}"
    const val KotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.Kotlin.gradle}"
    const val KotlinCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Kotlin.coroutines}"
    const val KotlinCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.coroutines}"

    const val RetrofitOkhttp = "com.squareup.okhttp3:okhttp:${Versions.Retrofit.okhttp}"
    const val RetrofitCore = "com.squareup.retrofit2:retrofit:${Versions.Retrofit.core}"
    const val RetrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.Retrofit.core}"
    const val RetrofitLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.Retrofit.loggingInterceptor}"
    const val RetrofitConverterScalars = "com.squareup.retrofit2:converter-scalars:${Versions.Retrofit.converterScalars}"

    const val GlideCore = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val GlideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    const val LifecycleExt = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val LifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val LifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"

    const val Hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val HiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val HiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.HiltAndroidX.viewModel}"
    const val HiltViewModelCompiler = "androidx.hilt:hilt-compiler:${Versions.HiltAndroidX.compiler}"
    const val HiltActivity = "androidx.activity:activity-ktx:${Versions.HiltAndroidX.activity}"
    const val HiltFragment = "androidx.fragment:fragment-ktx:${Versions.HiltAndroidX.fragment}"
    const val HiltTest = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
    const val HiltTestCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    const val Timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val jUnitTestExt = "androidx.test.ext:junit:${Versions.jUnitTestExt}"
    const val Espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val CoroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Kotlin.coroutinesTest}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"

    const val Paging = "androidx.paging:paging-runtime-ktx:${Versions.paging}"

    const val Lottie = "com.airbnb.android:lottie:${Versions.lottie}"
}