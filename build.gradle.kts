import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation("org.xerial:sqlite-jdbc:3.45.2.0")
    implementation ("org.slf4j:slf4j-nop:2.0.12")
    implementation("org.jetbrains.exposed", "exposed-core", "0.48.0")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.48.0")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.48.0")
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "schoolApp"
            packageVersion = "1.0.0"
        }
    }
}
