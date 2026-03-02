########################################
# General Android / Debuggability
########################################

# Preserve line numbers for crash reports
-keepattributes SourceFile,LineNumberTable

# Keep all annotations (used by Hilt & serialization)
-keepattributes *Annotation*

########################################
# Kotlin
########################################

# Required for reflection and kotlinx.serialization
-keep class kotlin.Metadata { *; }

########################################
# Hilt / Dagger
########################################

# Hilt-generated components and internals
-keep class dagger.hilt.** { *; }
-keep class dagger.hilt.android.internal.** { *; }

# javax.inject annotations
-keep class javax.inject.** { *; }

########################################
# kotlinx.serialization
########################################

# Keep generated serializers
-keepclassmembers class **$$serializer {
    <fields>;
}

# Keep Serializable classes and their members
-keep @kotlinx.serialization.Serializable class * { *; }

########################################
# Enums (safe default for serialization)
########################################

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
