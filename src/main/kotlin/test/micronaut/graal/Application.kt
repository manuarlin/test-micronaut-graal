package test.micronaut.graal

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("test.micronaut.graal")
                .mainClass(Application.javaClass)
                .start()
    }
}