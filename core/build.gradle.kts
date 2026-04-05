import org.gradle.kotlin.dsl.provideDelegate

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
