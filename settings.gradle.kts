pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            version("compose-bom", "2024.02.00")

            // Plugins
            plugin("android-application", "com.android.application").version("8.2.2")
            plugin("kotlin-android", "org.jetbrains.kotlin.android").version("1.9.0")
            plugin("google-services", "com.google.gms.google-services").version("4.4.0")

            // Dependencies versions
            version("core-ktx", "1.12.0")
            version("lifecycle-runtime", "2.7.0")
            version("activity-compose", "1.8.2")
            version("navigation", "2.7.7")
            version("coil", "2.5.0")
            version("firebase-bom", "32.7.2")

            // Dependencies
            library("androidx-core-ktx", "androidx.core", "core-ktx").versionRef("core-ktx")
            library("androidx-lifecycle-runtime-ktx", "androidx.lifecycle", "lifecycle-runtime-ktx").versionRef("lifecycle-runtime")
            library("androidx-activity-compose", "androidx.activity", "activity-compose").versionRef("activity-compose")
            library("androidx-compose-bom", "androidx.compose", "compose-bom").versionRef("compose-bom")
            library("androidx-ui", "androidx.compose.ui", "ui")
            library("androidx-ui-graphics", "androidx.compose.ui", "ui-graphics")
            library("androidx-ui-tooling", "androidx.compose.ui", "ui-tooling")
            library("androidx-ui-tooling-preview", "androidx.compose.ui", "ui-tooling-preview")
            library("androidx-material3", "androidx.compose.material3", "material3")
            library("androidx-navigation-compose", "androidx.navigation", "navigation-compose").versionRef("navigation")

            // Firebase
            library("firebase-bom", "com.google.firebase", "firebase-bom").versionRef("firebase-bom")
            library("firebase-auth-ktx", "com.google.firebase", "firebase-auth-ktx")
            library("firebase-firestore-ktx", "com.google.firebase", "firebase-firestore-ktx")
            library("firebase-storage-ktx", "com.google.firebase", "firebase-storage-ktx")

            // Coil
            library("coil-compose", "io.coil-kt", "coil-compose").versionRef("coil")
            library("coil-gif", "io.coil-kt", "coil-gif").versionRef("coil")
        }
    }
}

rootProject.name = "SellIt"
include(":app")