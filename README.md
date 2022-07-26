Ibadat SDk
===================
[![](https://jitpack.io/v/gakkmediabd/IbadatSDK.svg)](https://jitpack.io/#gakkmediabd/IbadatSDK)

## ScreenShot
<img src="https://github.com/gakkmediabd/IbadatSDK/blob/master/Screenshot_20220719-161130_IbadatSDK.jpg" width="300" height="500" />


**Used Dependency

    implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'com.google.android.gms:play-services-measurement:21.0.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
    implementation 'com.google.android.gms:play-services-maps:18.0.2'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'androidx.cardview:cardview:1.0.0'
    implementation "androidx.core:core-ktx:1.6.0"
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'jp.wasabeef:recyclerview-animators:4.0.2'
    implementation "com.squareup.retrofit2:retrofit:2.9.0"
    implementation "com.squareup.retrofit2:converter-gson:2.9.0"
    implementation "com.squareup.retrofit2:converter-scalars:2.9.0"
    implementation "com.google.code.gson:gson:2.8.6"
    implementation "androidx.navigation:navigation-fragment-ktx:2.4.2"
    implementation "androidx.navigation:navigation-ui-ktx:2.4.2"
    implementation 'com.github.msarhan:ummalqura-calendar:1.1.9'
    implementation 'com.google.android.gms:play-services-location:15.0.1'
    implementation 'com.github.bumptech.glide:glide:4.11.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.21"
    
## Setup
**Root level**
      
    allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
		}
    }

**Gradle**

    dependencies {
       implementation 'com.github.gakkmediabd:IbadatSDK:1.0.8'
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
