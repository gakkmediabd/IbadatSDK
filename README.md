Ibadat SDk
===================
[![](https://jitpack.io/v/gakkmediabd/IbadatSDK.svg)](https://jitpack.io/#gakkmediabd/IbadatSDK)

## ScreenShot
<img src="https://github.com/gakkmediabd/IbadatSDK/blob/master/Screenshot_20220719-161130_IbadatSDK.jpg" width="300" height="500" />


**Used Dependency

    implementation project(path: ':azan')
    implementation 'com.google.android.gms:play-services-measurement:21.0.0'
    implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
    implementation 'com.google.android.gms:play-services-maps:18.0.2'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'androidx.cardview:cardview:1.0.0'
    implementation 'androidx.core:core-ktx:1.2.0'
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'jp.wasabeef:recyclerview-animators:4.0.2'
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.2.0-alpha01"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.4.1"
    implementation "com.squareup.retrofit2:retrofit:2.9.0"
    implementation "com.squareup.retrofit2:converter-gson:2.9.0"
    implementation "com.squareup.retrofit2:converter-scalars:2.9.0"
    implementation "com.google.code.gson:gson:2.8.6"
    implementation "androidx.navigation:navigation-fragment-ktx:2.4.2"
    implementation "androidx.navigation:navigation-ui-ktx:2.4.2"
    implementation 'com.github.msarhan:ummalqura-calendar:1.1.9'
    implementation 'com.google.android.gms:play-services-location:15.0.1'
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    implementation 'com.github.bumptech.glide:glide:4.11.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
    
## Setup
**Root level**

    buildFeatures {
        dataBinding true
    }
    
    implementation "androidx.lifecycle:lifecycle-extensions:2.2.0"
      
    allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
		}
    }

**Gradle**

    dependencies {
       implementation 'com.github.gakkmediabd:IbadatSDK:1.0.7'
    }

## Usage

More usages about ibadatSDK, please see the sample.

Open Dua

    IbadatSdkCore.openFeature(this, IbadatSdkCore.DUA)

Open Hadith

    IbadatSdkCore.openFeature(this, IbadatSdkCore.HADITH)

Open Hadith

    IbadatSdkCore.openFeature(this, IbadatSdkCore.SURAH)

Open Hadith

    IbadatSdkCore.openFeature(this, IbadatSdkCore.TASBIH)

Open Hadith

    IbadatSdkCore.openFeature(this, IbadatSdkCore.SALATLEARNING)

[More about developer complany: Gakk Media BD Ltd <img src="https://gakkmedia.com/wp-content/uploads/2019/03/Gakk-Media_Logo-final-black.png" width="200" height="40" />](https://gakkmedia.com/)
