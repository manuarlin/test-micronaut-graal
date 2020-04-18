package test.micronaut.graal

import io.micronaut.core.annotation.Introspected

@Document
@Introspected
data class Item(var category: String, var price: String, var stocked: Boolean, var name: String)