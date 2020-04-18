package test.micronaut.graal

import com.mongodb.reactivestreams.client.MongoClient
import io.kotlintest.matchers.collections.shouldContainAll
import io.kotlintest.specs.BehaviorSpec
import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest.GET
import io.micronaut.http.client.RxStreamingHttpClient
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import io.reactivex.Single

@MicronautTest
class ItemControllerTest : BehaviorSpec({

    val embeddedServer: EmbeddedServer = ApplicationContext.run(EmbeddedServer::class.java)
    val httpClient: RxStreamingHttpClient = RxStreamingHttpClient.create(embeddedServer.url)
    val mongoClient: MongoClient = embeddedServer.applicationContext.getBean(MongoClient::class.java)

    given("the item controller") {

        val expectedItems = listOf(
                Item("Sporting Goods", "\$49.99", true, "Football"),
                Item("Sporting Goods", "\$9.99", true, "Baseball"),
                Item("Sporting Goods", "\$29.99", false, "Basketball"),
                Item("Electronics", "\$99.99", true, "iPod Touch"),
                Item("Electronics", "\$399.99", false, "iPhone 5"),
                Item("Electronics", "\$199.99", true, "Nexus 7")
        )

        Single.fromPublisher(mongoClient
                .getDatabase("test")
                .getCollection("item", Item::class.java)
                .insertMany(expectedItems)
        ).blockingGet()

        `when`("the controller is called to get all items") {
            val items = httpClient.toBlocking().retrieve(GET<Unit>("/items"), Argument.listOf(Item::class.java)).toList()
            then("all items should be returned") {
                items shouldContainAll expectedItems
            }
        }
    }
})