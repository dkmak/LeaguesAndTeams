########################################
# kotlinx.serialization (library contracts)
########################################

# Keep serializers for exposed Serializable models
-keepclassmembers class **$$serializer {
    <fields>;
}

# Keep public Serializable models
-keep @kotlinx.serialization.Serializable class * { *; }

########################################
# Hilt / DI (if module exposes bindings)
########################################

# Keep Hilt metadata for consumers
-keep class dagger.hilt.internal.** { *; }
