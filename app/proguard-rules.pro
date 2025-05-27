# Исключения для всех аннотированных классов @Keep
-keep @interface androidx.annotation.Keep
-keep class ** {
    @androidx.annotation.Keep *;
}

# Игнорировать отсутствующие в Android-рантайме JDK-классы
-dontwarn java.beans.**
-dontwarn javax.naming.**
-dontwarn javax.naming.directory.**

# Игнорировать старые JSSE/Conscrypt-реализации
-dontwarn com.android.org.conscrypt.**
-dontwarn org.apache.harmony.xnet.provider.jsse.**

# Игнорировать BouncyCastle JSSE и OpenJSSE
-dontwarn org.bouncycastle.jsse.**
-dontwarn org.openjsse.**

# Игнорировать внутренние платформенные адаптеры OkHttp
-dontwarn okhttp3.internal.platform.**

# Игнорируем отсутствующие в Android-рантайме аннотации ErrorProne
-dontwarn com.google.errorprone.annotations.**