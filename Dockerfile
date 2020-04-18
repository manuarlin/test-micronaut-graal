FROM oracle/graalvm-ce:20.0.0-java8 as graalvm
# For JDK 11
#FROM oracle/graalvm-ce:20.0.0-java11 as graalvm
RUN gu install native-image

COPY . /home/app/test-micronaut-graal
WORKDIR /home/app/test-micronaut-graal

RUN native-image --no-server -cp build/libs/test-micronaut-graal-*-all.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/test-micronaut-graal/test-micronaut-graal /app/test-micronaut-graal
ENTRYPOINT ["/app/test-micronaut-graal"]
