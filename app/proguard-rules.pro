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

# Ofuscation

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations

# Required classes
-keepattributes LineNumberTable,SourceFile
-keep public class * extends java.lang.Exception

-keep public class com.coppel.rhconecta.dev.business.models.** { *; }
-keep public class com.coppel.rhconecta.dev.business.Enums.** { *; }
-keep public class com.coppel.rhconecta.dev.data.** { *; }
-keep public class noman.weekcalendar.** { *; }
-keep public class com.wdullaer.datetimepickerholiday.** { *; }
-keep public class com.wdullaer.materialdatepicker.** { *; }
-keep public class com.shrikanthravi.collapsiblecalendarview.** { *; }
-keep public class me.dm7.barcodescanner.** { *; }

# -keep public class com.coppel.rhconecta.dev.data.authentication.model.login.** { *; }

# Classes types

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

-keepnames class * implements java.io.Serializable

# Retrofit

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keepclasseswithmembers interface * {
    @retrofit2.http.* <methods>;
}

# GOOGLE

-keep public class com.google.android.gms.* { public *; }
-dontwarn com.google.android.gms.**

-keep public class com.google.firebase.* { public *; }
-dontwarn com.google.firebase.**

-keep public class com.google.firebase.iid.* { public *; }
-dontwarn com.google.firebase.idd.**

# LOG

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}

-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

# Movements
-keep public class com.coppel.rhconecta.dev.framework.movements.* { public *; }
-dontwarn com.coppel.rhconecta.dev.framework.movements.**