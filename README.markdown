# HDM-Haushaltsbuch

1. [Install eclipse tools](https://developer.ibm.com/wasdev/getstarted/)
1. [Add a liberty-profile server](https://developer.ibm.com/wasdev/docs/developing-applications-wdt-liberty-profile/)
1. Import the app into Eclipse
1. Run on Server => Bluemix
1. Add the ElephantSQL server
1. Deploy

## Local Development

* `VCAP_SERVICES` is not set locally. Add an entry to `/usr/local/opt/was-liberty/usr/servers/defaultServer/server.env` where `/usr/local/opt/was-liberty` is the installation directory of your local liberty server, and `defaultServer` is the name of the server you chose when launching it.