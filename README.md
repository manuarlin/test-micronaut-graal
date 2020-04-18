### Simple Micronaut Graal Mongo example

To run the application (accessing a local MongoDB) :
```shell script
./docker-build.sh
docker run -p 8080:8080 --net=host test-micronaut-graal
```