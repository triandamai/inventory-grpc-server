rootProject.name = "grpc-learn"

include(":protos")
include(":server")
include(":stub")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenLocal()
        mavenCentral()
        google()
    }
}