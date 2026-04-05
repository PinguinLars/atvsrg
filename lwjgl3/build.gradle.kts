@file:Suppress( "UnusedImport")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import io.github.fourlastor.construo.Target

val enableGraalNative: String by project
buildscript {
  repositories {
    gradlePluginPortal()
  }
  dependencies {
    val enableGraalNative: String by project
    if (enableGraalNative == "true") classpath("org.graalvm.buildtools.native:org.graalvm.buildtools.native.gradle.plugin:0.9.28")
  }
}
plugins {
  application
  alias(libs.plugins.construo)
  kotlin("jvm")
}



sourceSets.main {
  resources.srcDirs += rootProject.file("assets")
}

application.mainClass = "me.ashypinguin.atvsrg.lwjgl3.Lwjgl3Launcher"
application.applicationName = "atvsrg"
eclipse.project.name = "atvsrg-lwjgl3"
java.sourceCompatibility = JavaVersion.VERSION_21
java.targetCompatibility = JavaVersion.VERSION_21
//if (JavaVersion.current().isJava9Compatible()) compileJava.options.release.set(21)
if (JavaVersion.current().isJava9Compatible) tasks.withType<JavaCompile>().configureEach { options.release.set(21) }
kotlin.compilerOptions.jvmTarget.set(JvmTarget.JVM_21)

dependencies {
  implementation(libs.gdx.lwjgl3)
  implementation(variantOf(libs.gdx.box2d.platform) { classifier("natives-desktop") })
  implementation(variantOf(libs.gdx.freetype.platform) { classifier("natives-desktop") })
  implementation(variantOf(libs.gdx.platform) { classifier("natives-desktop") })
  implementation(project(":core"))

  if (enableGraalNative == "true") implementation(libs.graal.helper.lwjgl3)
  // Forces LWJGL3 to use at least $lwjgl3Version, currently 3.4.1, to avoid problems on Java 25 and up.
  constraints { implementation(libs.bundles.lwjgl) }
}

val os = System.getProperty("os.name").lowercase()

tasks.run {
  workingDir = rootProject.file("assets")
// You can uncomment the next line if your IDE claims a build failure even when the app closed properly.
  //setIgnoreExitValue(true)
  jvmArgs("--enable-native-access=ALL-UNNAMED")
  if (os.contains("mac")) jvmArgs("-XstartOnFirstThread")
}

tasks.jar {
// sets the name of the .jar file this produces to the name of the game or app, with the version after.
  archiveFileName.set("${project.name}-${project.version}.jar")
// the duplicatesStrategy matters starting in Gradle 7.0; this setting works.
  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
  dependsOn(configurations.runtimeClasspath)
  from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
// these "exclude" lines remove some unnecessary duplicate files in the output JAR.
  exclude("META-INF/INDEX.LIST", "META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
  dependencies {
    exclude("META-INF/INDEX.LIST", "META-INF/maven/**")
  }
// setting the manifest makes the JAR runnable.
// enabling native access helps avoid a warning when Java 24 or later runs the JAR.
// setting Multi-Release to true allows LWJGL3 to use different classes on recent Java versions.
  manifest {
    attributes(
      "Main-Class" to application.mainClass.get(),
      "Enable-Native-Access" to "ALL-UNNAMED",
      "Multi-Release" to "true"
    )
  }
// this last step may help on some OSes that need extra instruction to make runnable JARs.
  doLast {
    file(archiveFile).setExecutable(true, false)
  }
}

// Builds a JAR that only includes the files needed to run on macOS, not Windows or Linux.
// The file size for a Mac-only JAR is about 7MB smaller than a cross-platform JAR.
tasks.register<Jar>("jarMac") {
  group = "build"
  dependsOn("jar")

  archiveFileName.set("${project.name}-${project.version}-mac.jar")

  from(sourceSets.main.get().output)
  from({ configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) } })

  exclude(
    "windows/x86/**", "windows/x64/**", "linux/arm32/**", "linux/arm64/**", "linux/x64/**", "**/*.dll", "**/*.so",
    "META-INF/INDEX.LIST", "META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA"
  )
}

// Builds a JAR that only includes the files needed to run on Linux, not Windows or macOS.
// The file size for a Linux-only JAR is about 5MB smaller than a cross-platform JAR.
tasks.register<Jar>("jarLinux") {
  group = "build"
  dependsOn("jar")

  archiveFileName.set("${project.name}-${project.version}-linux.jar")

  from(sourceSets.main.get().output)
  from({ configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) } })

  exclude(
    "windows/x86/**", "windows/x64/**", "macos/arm64/**", "macos/x64/**", "**/*.dll", "**/*.dylib",
    "META-INF/INDEX.LIST", "META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA"
  )
}

// Builds a JAR that only includes the files needed to run on Windows, not Linux or macOS.
// The file size for a Windows-only JAR is about 6MB smaller than a cross-platform JAR.
tasks.register<Jar>("jarWin") {
  group = "build"
  dependsOn("jar")

  archiveFileName.set("${project.name}-${project.version}-win.jar")

  from(sourceSets.main.get().output)
  from({ configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) } })

  exclude(
    "macos/arm64/**", "macos/x64/**", "linux/arm32/**", "linux/arm64/**", "linux/x64/**", "**/*.dylib", "**/*.so",
    "META-INF/INDEX.LIST", "META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA"
  )
}

construo {
  // name of the executable
  name.set(project.name)
  // human-readable name, used for example in the `.app` name for macOS
  humanName.set(project.name)
  jlink {
    guessModulesFromJar.set(false)
    // You may need to add more modules, as needed.
    modules.addAll("java.base", "java.management", "java.desktop", "jdk.unsupported")
  }

  targets {
    register<Target.Linux>("linuxX64") {
      architecture.set(Target.Architecture.X86_64)
      jdkUrl.set("https://github.com/adoptium/temurin21-binaries/releases/download/jdk-21.0.10%2B7/OpenJDK21U-jdk_x64_linux_hotspot_21.0.10_7.tar.gz")
      // Linux does not currently have a way to set the icon on the executable
    }
    register<Target.MacOs>("macM1") {
      architecture.set(Target.Architecture.AARCH64)
      jdkUrl.set("https://github.com/adoptium/temurin21-binaries/releases/download/jdk-21.0.10%2B7/OpenJDK21U-jdk_aarch64_mac_hotspot_21.0.10_7.tar.gz")
      // macOS needs an identifier
      identifier.set("me.ashypinguin.atvsrg")
      // Optional: icon for macOS, as an ICNS file
      macIcon.set(project.file("icons/logo.icns"))
    }
    register<Target.MacOs>("macX64") {
      architecture.set(Target.Architecture.X86_64)
      jdkUrl.set("https://github.com/adoptium/temurin21-binaries/releases/download/jdk-21.0.10%2B7/OpenJDK21U-jdk_x64_mac_hotspot_21.0.10_7.tar.gz")
      // macOS needs an identifier
      identifier.set("me.ashypinguin.atvsrg")
      // Optional: icon for macOS, as an ICNS file
      macIcon.set(project.file("icons/logo.icns"))
    }
    register<Target.Windows>("winX64") {
      architecture.set(Target.Architecture.X86_64)
      // Optional: icon for Windows, as a PNG
      icon.set(project.file("icons/logo.png"))
      jdkUrl.set("https://github.com/adoptium/temurin21-binaries/releases/download/jdk-21.0.10%2B7/OpenJDK21U-jdk_x64_windows_hotspot_21.0.10_7.zip")
      // Uncomment the next line to show a console when the game runs, to print messages.
      //useConsole.set(true)
    }
  }
}

// Equivalent to the jar task; here for compatibility with gdx-setup.
tasks.register<Jar>("dist") {
  dependsOn("jar")
}

distributions {
  main {
    contents {
      into("libs") {
        val jarName = tasks.jar.get().outputs.files.singleFile.name

        /*project.*/configurations.runtimeClasspath.get().files
        .filter { it.name != jarName }
        .forEach { exclude(it.name) }
      }
    }
  }
}

//startScripts.dependsOn(":lwjgl3:jar")
//startScripts.classpath = project.tasks.jar.outputs.files
tasks.named<CreateStartScripts>("startScripts") {
  dependsOn(":lwjgl3:jar")
  classpath = tasks.jar.get().outputs.files
}

if (enableGraalNative == "true") apply(from = file("nativeimage.gradle"))
