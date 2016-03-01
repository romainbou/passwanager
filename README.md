# Passwanager
A passwords and credentials manager without the key management.

## How it works?
On singup, a keypair is generated client-side. The user has to keep the private key himself. It will be asked each time a sensitive data is requested.

## Getting started

### Database configuration
Required: MySQL database

Use the file `passwanager.sql` in the root folder to import the database
Create a mysql user and database called `passwanager`
Set its password to `i4BKwffH244SdtfR` or change it in the file `src/main/resources/META-INF/persistence.xml`

### Build the client
The web client is situated in `src/main/webapp` and is independant from the server. The serve only serves the API and the client's static files.
The client needs to be built (bower is required)
```
$ cd src/main/webapp
$ bower install
```

### Build and Run the server

```
$ mvn clean install
$ mvn jetty:run
```

Launch your browser to `http://localhost:8080`

## Features
* Add folders with collaborators

## TODO
* Send each private keys of a folder's members to let a user encrypt a password for each member
