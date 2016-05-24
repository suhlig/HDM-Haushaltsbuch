# HDM-Haushaltsbuch

## Running it

1. [Install eclipse tools](https://developer.ibm.com/wasdev/getstarted/)
1. [Add a liberty-profile server](https://developer.ibm.com/wasdev/docs/developing-applications-wdt-liberty-profile/) for local testing
1. Import the app into Eclipse
1. Run on Server => Bluemix
1. Add the ElephantSQL server
1. Deploy. The app will auto-create the tables.

## Tests

* There are unit- and integration tests that can be run from Eclipse
* The integration- and acceptance tests require a local Postgres
* Acceptance tests require ruby. See the separate [README](test/test/acceptance/README.markdown).

## Local Development

### Database connection

`VCAP_SERVICES` is not set when developing locally. Add an entry to `/usr/local/opt/was-liberty/usr/servers/defaultServer/server.env` where `/usr/local/opt/was-liberty` is the installation directory of your local liberty server, and `defaultServer` is the name of the server you chose when launching it.

In Eclipse, this file is also editable in the "Servers" view as `server.env`.

### Local PostgreSQL server

Add the currently logged on user as a new (password-less) PostgreSQL user:

  ```
  # create a user
  $ postgres -c "createuser --createdb --no-superuser --no-createrole $USER"
  
  # start postgres
  $ PGDATA=/usr/local/var/postgres postgres
  
  # create the user's database (prereq for tests)
  $ createdb
  
  # create the database
  $ createdb haushaltsbuch
  ```

If the database URL is specified without a host component (e.g. `postgres:///haushaltsbuch`), a local domain socket will be used for the Postgres connection. This avoids having to provide a separate password for the TCP connection.

The entry in server.env would then be this:

```
VCAP_SERVICES={"elephantsql":[{"credentials":{"uri":"postgres:///haushaltsbuch"}}]}
```

## Design Notes

* Input validation happens at two places: client-side (HTML form validation), where we are nice with the user, and at the database level, where we make sure only valid data enters the system. Right now there is no need for additional validation on the domain layer.
