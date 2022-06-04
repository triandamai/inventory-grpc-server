import com.google.protobuf.gradle.*;
buildscript {
	dependencies {
		classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.18")
    }
}

plugins{
	kotlin("jvm") version "1.6.21"
	id("com.google.protobuf") version "0.8.13" apply false
}

allprojects {
	repositories {
		gradlePluginPortal()
		google()
		mavenLocal()
		mavenCentral()
		google()
	}
}