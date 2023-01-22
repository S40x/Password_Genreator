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
-keepclassmembers enum * { *; }
-keep interface ir.tapsell.sdk.NoProguard

-keep interface ir.tapsell.sdk.NoNameProguard
-keep class * implements ir.tapsell.sdk.NoProguard { *; }

-keep enum * implements ir.tapsell.sdk.NoProguard { *; }

-keepnames class * implements ir.tapsell.sdk.NoNameProguard { *; }
-keep class ir.tapsell.sdk.nativeads.TapsellNativeVideoAdLoader$Builder {*;}
-keep class ir.tapsell.sdk.nativeads.TapsellNativeBannerAdLoader$Builder {*;}
-keep interface com.android.vending.billing.IInAppBillingService
-keep class * implements com.android.vending.billing.IInAppBillingService {*;}

-keep class ir.tapsell.sdk.models.** { *; }

-keep class ir.tapsell.sdk.sentry.model.** {*;}

# To Remove Logger Class (Todo: Replace Logger Class with LogUtils)
-assumenosideeffects class ir.tapsell.sdk.logger.Logger {
    public * ;
    public static *** LogDebug(...);
    public static *** LogError(...);
    public static *** LogInfo(...);
    public static *** LogVerbose(...);
    public static *** LogWarn(...);
}

-keep public class com.bumptech.glide.**

