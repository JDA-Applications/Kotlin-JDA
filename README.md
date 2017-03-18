# Kotlin-JDA

JDA: ![ [jda-version](https://github.com/DV8FromTheWorld/JDA) ](https://camo.githubusercontent.com/e7694321b7100c71210308a64c77851c6929aca9/68747470733a2f2f6170692e62696e747261792e636f6d2f7061636b616765732f64763866726f6d746865776f726c642f6d6176656e2f4a44412f696d616765732f646f776e6c6f61642e737667)<br>
Kotlin-JDA: ![ [jitpack](https://img.shields.io/badge/jitpack-download-brightgreen.svg) ](https://jitpack.io/#JDA-Applications/Kotlin-JDA)

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