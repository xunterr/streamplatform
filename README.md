# streamplatform

Streamplatform helps you manage and secure your video streams.

## So, how it works?

To create streams, users must first register in your system. Once a stream is created, the API provides a unique one-off secret key for initiating streams. When users go to the stream server to start a stream, your server should use the API's callback. The API verifies stream information, then sends a public key to your server. This public key serves as the stream ID, which should be used to modify the stream URL, ensuring security against interception.

*Note: The terms `secret key` and `public key` are used outside the RSA context, and no key-pair is generated.*


## Authentication and Authorization

The API defaults to utilizing Keycloak for authentication and authorization. Configure your authentication service and set the appropriate URI in the `issuer-uri` and `jwk-set-uri` fields within  [`application.properties`](https://github.com/xunterr/streamplatform/blob/master/stream/src/main/resources/application.properties).

*Note: if you using another authentication provider, make sure it supports OAUTH2*

## Adding Support for Additional Servers

The API use [NGINX RTMP module](https://github.com/arut/nginx-rtmp-module) by default. However, you still can use it with another services. Just make sure to:

1. Use API's callback when user start and end a stream.
2. Handle the 302 status code with the new stream ID returned by the API and update it in your service.

## Infrastructure 

This project integrates components such as the Eureka discovery service and API gateway. These are optional and can be removed.

## Deploying

1. Clone repository
2. Make sure you setup your auth provider and specified it in `application.properties`
3. Run Docker Compose (remember to change database username and password). Alternatively, you can deploy it without Docker Compose, just ensure that you set up the database accordingly.

## TODO: 

- [ ] Write a docs 

- [ ] Write tests

- [ ] Write adapters for different RTMP servers.

## Contributing

We welcome contributions! If you encounter issues or have enhancement ideas, please open an issue or submit a pull request.

