# HDM-Haushaltsbuch

1. [Install eclipse tools](https://developer.ibm.com/wasdev/getstarted/)
1. [Add a liberty-profile server](https://developer.ibm.com/wasdev/docs/developing-applications-wdt-liberty-profile/) for local testing
1. Import the app into Eclipse
1. Run on Server => Bluemix
1. Add the ElephantSQL server
1. Deploy

## Local Development

`VCAP_SERVICES` is not set when developing locally. Add an entry to `/usr/local/opt/was-liberty/usr/servers/defaultServer/server.env` where `/usr/local/opt/was-liberty` is the installation directory of your local liberty server, and `defaultServer` is the name of the server you chose when launching it.

Alternatively, install, configure and start a local PostgreSQL server:

* Add the currently logged on user as a new (password-less) PostgreSQL user:

  ```
  $ postgres -c "createuser --createdb --no-superuser --no-createrole $USER"
  $ createdb haushaltsbuch
  ```

* If the database URL is specified without a host component (e.g. `postgres:///haushaltsbuch`), a local domain socket will be used for the Postgres connection. This avoids having to provide a separate password for the TCP connection.
