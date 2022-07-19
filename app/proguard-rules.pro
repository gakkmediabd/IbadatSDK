# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
-keep class androidx.* {*;}
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
  }
-keep,allowobfuscation interface com.google.gson.annotations.SerializedName

-keep class org.xmlpull.v1.* {*;}
-keep class com.ibadat.sdk.data.model.** { *; }
-keep public class * extends java.lang.Exception
# keep everything in this package from being renamed only
-keepnames class com.ibadat.sdk.data.model.** { *; }
-keep class com.ibadat.sdk.adapter.** { *; }
-keep class com.ibadat.sdk.player.data.model.** { *; }
-keepnames class com.ibadat.sdk.player.data.model.** { *; }
# keep everything in this package from being renamed only
-keepnames class com.ibadat.sdk.adapter.** { *; }
-dontwarn retrofit.**
-keep class retrofit.* { *; }
-keep class com.google.android.* {*;}
-keep class androidx.core.app.CoreComponentFactory { *; }
-keep class android.content.Context.*{*;}
-keep class android.content.Intent.*{*;}
# Remove logs, don't forget to use 'proguard-android-optimize.txt' file in build.gradle
-assumenosideeffects class android.util.Log {
    public static int d(...);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
    public static int wtf(...);
}