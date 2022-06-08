package dependencies.android

import dependencies.implementation
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.navGraph() {
    val version = "2.4.2"
    implementation ("androidx.navigation:navigation-fragment-ktx:$version")
    implementation ("androidx.navigation:navigation-ui-ktx:$version")
}