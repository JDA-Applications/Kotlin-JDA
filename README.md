# Kotlin-JDA

JDA: [ ![jda](https://api.bintray.com/packages/dv8fromtheworld/maven/JDA/images/download.svg) ](https://github.com/DV8FromTheWorld/JDA)<br>
Kotlin-JDA: [ ![jitpack](https://jitpack.io/v/JDA-Applications/Kotlin-JDA.svg) ](https://jitpack.io/#JDA-Applications/Kotlin-JDA)

A kotlin interface for JDA, providing extensions and convenience for kotlin based projects that want to interact with JDA

## Setup

### Gradle

```gradle
repositories {
    jcenter()
    maven { url 'https://jitpack.io' }
}

dependencies {
    compile 'net.dv8tion:JDA:3.0.0_156'
    compile 'com.github.JDA-Applications:Kotlin-JDA:master-SNAPSHOT'
}
```

> Replace the JDA version if needed<br>
> Kotlin setup is up to you!

### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
    <repository>
        <id>jcenter</id>
        <name>jcenter-bintray</name>
        <url>http://jcenter.bintray.com</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>com.github.JDA-Applications</groupId>
        <artifactId>Kotlin-JDA</artifactId>
        <version>master-SNAPSHOT</version>
    </dependency>
    <dependency>
        <groupId>net.dv8tion</groupId>
        <artifactId>JDA</artifactId>
        <version>3.0.0_156</version>
    </dependency>
</dependencies>
```

> Replace the JDA version if needed<br>
> Kotlin setup is up to you!
