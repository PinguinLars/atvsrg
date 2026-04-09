import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

tasks.withType<JavaCompile>().configureEach { options.encoding = "UTF-8" }
eclipse.project.name = "atvsrg-core"

val enableGraalNative: String by project

dependencies {
  api(libs.ashley)
  api(libs.bundles.gdx.core)
  api(libs.vis.ui)
  api(libs.bundles.ktx.core)
  api(libs.artemis)
  api(libs.kotlin.stdlib)
  api(libs.kotlin.coroutines)

  if (enableGraalNative == "true") implementation(libs.graal.helper)
}

tasks.jar {
  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions { freeCompilerArgs.set(listOf("-Xexplicit-backing-fields")) }
