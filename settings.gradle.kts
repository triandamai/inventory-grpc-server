rootProject.name = "inventory-grpc-server"

include(":protos")
include(":app-server")
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