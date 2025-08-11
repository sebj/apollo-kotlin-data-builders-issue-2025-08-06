import com.apollographql.apollo.annotations.ApolloExperimental

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.apollo)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)

    implementation(libs.bundles.apollo)
}

apollo {
    service("service") {
        addTypename.set("always")
        codegenModels = "responseBased"
        failOnWarnings = true
//        generateDataBuilders = true
        packageName = "com.example.myapplication.api"

        @OptIn(ApolloExperimental::class)
        plugin("com.apollographql.cache:normalized-cache-apollo-compiler-plugin:1.0.0-alpha.5") {
            argument("com.apollographql.cache.packageName", packageName.get())
        }

        dataBuildersOutputDirConnection {
            connectToKotlinSourceSet("main")
        }

        schemaFiles.from(
            "src/main/graphql/schema.graphqls",
            "src/main/graphql/extensions.graphqls"
        )
    }
}