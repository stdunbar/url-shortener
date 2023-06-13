# Simple URL shortner service using Quarkus
A small web service that acts as a URL shortner (like [bit.ly](https://bit.ly)).  There are two
services deployed, one to shorten and one to lengthen the URL.

It's important to note that the system attempts to make the URL's less predictable but the shortened
URL is not anywhere close to cryptographically secure.  If someone had unlimited access to the link
generation it would not take too much to understand the algorithm.

At this time there is no security on this service.  It would be pretty easy to abuse though there really
isn't too much you can do with it.

## Deployment
----

To build "normally", simply run

```mvn clean package```

This will run the unit tests and create a jar file that can be run through Maven in dev mode.  Run:

```shell
mvn quarkus:dev
```

after you build to start in the dev mode.

.  It produces
`target/url-shortner-1.0.0-SNAPSHOT.jar` that can then be run with
`java -jar target/url-shortner-1.0.0-SNAPSHOT.jar`.



## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
mvn quarkus:dev
```

The application listens on port 8080.

## Creating a native executable

You need GraalVM and Docker in your environment to run this.

You can create a native executable using: `mvn package -Pnative`.

Or you can use Docker to build the native executable using: `mvn package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your binary: `./target/url-shortner-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult [the Quarkus guide](https://quarkus.io/guides/building-native-image-guide).

## Using the service

The code is inspired by the somewhat poor documentation that
[bit.ly has](https://dev.bitly.com/api-reference/#createBitlink).  bit.ly doesn't explain things very well so I took
what I could from them.

To request a short link, POST something like:

```json
    {
      "long_url": "https://www.hotjoe.com/"
    }
```

to the `/v1/shorten` URL.  The code will also accept the JSON elements `group_guid` and `domain`  but does not use them.
You will get a response back that looks something like:

```json
    {
        "id": "1886598168",
        "longUrl": "https://www.hotjoe.com/",
        "link": "https://hotjoe.io/cdP8wS",
        "createdAt": "2022-07-26T22:51:42.827990Z"
}
```

See below for the short link domain configuration.

To lengthen a URL, do a GET to the `link` field in the JSON response.  If the link is valid (i.e., hasn't expired or
been used too many times) you will get a 302 (redirect) back with the `Location` header set to the long url.

### Configuration
As a Quarkus application you can configure the run time parameters in standard ways.  
