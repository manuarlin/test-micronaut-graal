package test.micronaut.graal

import com.mongodb.reactivestreams.client.MongoClient
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.reactivestreams.Publisher


@Controller("/items")
class ItemController(private val mongoClient: MongoClient) {

    @Get
    fun getItems(): Publisher<Item> {
        return mongoClient
                .getDatabase("test")
                .getCollection("item", Item::class.java)
                .find()
    }

}