# Helidon SE Examples

Helidon SE のコンポーネントを一通り検証するサンプルコードです。

## Build and run

With JDK11+

```bash
# Maven
$ mvn package
$ java -jar target/helidon-se-examples.jar

# Gradle
$ gradle build
$ java -jar build/libs/helidon-se-examples.jar
```

## Build and run with Docker

```bash
# Executable JAR
$ docker build -t <your-registry-name>/helidon-se-examples:v1.0 -f Dockerfile .
$ docker run -p 8080:8080 <your-registry-name>/helidon-se-examples:v1.0

# jlink
$ docker build -t <your-registry-name>/helidon-se-examples-jlink:v1.0 -f Dockerfile.jlink .
$ docker run -p 8080:8080 <your-registry-name>/helidon-se-examples-jlink:v1.0

# Native Image
$ docker build -t <your-registry-name>/helidon-se-examples-native:v1.0 -f Dockerfile.native .
$ docker run -p 8080:8080 <your-registry-name>/helidon-se-examples-native:v1.0
```

## Exercise the application

```bash
curl -X GET http://localhost:8080/greet
{"message":"Hello World!"}
```

## Try health and metrics

```bash
curl -s -X GET http://localhost:8080/health
{"outcome":"UP",...
. . .
```

## Try metrics

```bash
# Prometheus Format
curl -s -X GET http://localhost:8080/metrics
# TYPE base:gc_g1_young_generation_count gauge
. . .

# JSON Format
curl -H 'Accept: application/json' -X GET http://localhost:8080/metrics
{"base":...
. . .
```
