-printconfiguration ~/tmp/full-r8-config.txt
-optimizationpasses 3
-allowaccessmodification

-keep @javax.inject.Qualifier public class *
-dontwarn **.R$*

-keepclassmembers class **.api.model.** { *; }
-keepclassmembers class **.api.http.** { *; }

-dontwarn **$DefaultImpls
-dontnote android.widget.AutoCompleteTextView
-dontwarn module-info

# Preserve annotations, line numbers, and source file names
-keepattributes Signature,Exceptions,InnerClasses,EnclosingMethod,*Annotation*,LineNumberTable,SourceFile

-keepclassmembers enum * {
    <fields>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Kotlin
-dontnote kotlin.internal.**
-dontwarn kotlinx.coroutines.**
-keep class kotlin.coroutines.Continuation

# Retrolambda
-dontwarn java.lang.invoke.*
-dontwarn **$$Lambda$*

# Gson & serialization
-keep class sun.misc.Unsafe { *; }
-dontnote com.google.gson.**
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class * {
 @com.google.gson.annotations.SerializedName <fields>;
}
-keep public class * implements com.google.gson.JsonSerializer


# RxJava
-dontnote  rx.internal.**
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# RxJava2
-dontwarn io.reactivex.internal.**

# Retrofit
-dontwarn okhttp3.**
-dontnote okhttp3.**
-dontwarn okio.**
-dontwarn retrofit2.**
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-dontwarn com.squareup.picasso.**
-dontwarn org.conscrypt.**
-keep public class * extends okhttp3.OkHttpClient{*;}
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-if interface * { @retrofit2.http.* public *** *(...); }
-keep,allowoptimization,allowshrinking,allowobfuscation class <3>
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response

# org.slf4j
-dontwarn org.slf4j.**

# ServiceLoader support
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Most of volatile fields are updated with AFU and should not be mangled
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# Keep annotation default values (e.g., retrofit2.http.Field.encoded).
-keepattributes AnnotationDefault

-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-keep class hilt_aggregated_deps.** { *; }