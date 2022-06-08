package dependencies

import dependencies.android.androidPaging
import dependencies.android.androidX
import dependencies.android.navGraph
import dependencies.android.vmLifeCycle
import dependencies.kotlin.coroutine
import dependencies.retrofit_okhttp.gson
import dependencies.retrofit_okhttp.okHttp
import dependencies.retrofit_okhttp.retrofit
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.appLibraries() {
    androidX()
    androidPaging()
    vmLifeCycle()
    navGraph()
    coroutine()
    materialDesign()
    testUnit()
    gson()
    okHttp()
    retrofit()
    glide()
    gander()
    daggerHilt()
}