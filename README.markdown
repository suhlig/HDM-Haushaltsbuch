# HDM-Haushaltsbuch

## Run

1. [Install eclipse tools](https://developer.ibm.com/wasdev/getstarted/)
1. [Add a liberty-profile server](https://developer.ibm.com/wasdev/docs/developing-applications-wdt-liberty-profile/) for local testing
1. Import the app into Eclipse
1. Add a 'turtle'-sized ElephantSQL service instance
1. Run on Server => Bluemix (the database tables will be auto-created)

Addiional information about the Eclipse tools is available at the Bluemix [https://console.ng.bluemix.net/docs/manageapps/eclipsetools/eclipsetools.html](documentation) site.

## Exercises

Using TDD, implement the following features:

1. Show a list of all categories at `/categories/all`
1. Show all entries of a certain category at  `/entries/by-category`

For each feature, add unit, integration and acceptance tests.

## Tests

* There are unit- and integration tests that can be run from Eclipse
* The integration- and acceptance tests require a local Postgres
* Acceptance tests require ruby. See the separate [README](test/test/acceptance/README.markdown).

## Local Development

### Database connection

Bluemix provides the datasource for a bound service in JDNI. We could configure the local dev server in the same way, but to make development even simpler, we take a shortcut and [manually initialize the PostgreSQL JDBC driver](https://jdbc.postgresql.org/documentation/94/use.html) when testing locally. The code does this automatically; there are no manual steps to be taken.

### Local PostgreSQL server

Add the currently logged on user as a new (password-less) PostgreSQL user:

  ```
  # create a user
  $ postgres -c "createuser --createdb --no-superuser --no-createrole $USER"

  # start postgres
  $ postgres -D /usr/local/var/postgres

  # create the user's database (prereq for tests)
  $ createdb

  # create the database
  $ createdb haushaltsbuch
  ```

If the database URL is specified without a host component (e.g. `postgres:///haushaltsbuch`), a local domain socket will be used for the Postgres connection. This avoids having to provide a separate password for the TCP connection.

The entries table auto-generates the UUID on insert. This requires the `uuid-ossp` extension to be available. It will be enabled automatically.

### PostgreSQL Statement Logging

In order to see the raw SQL statements, logging can be enabled for the local `postgres` process:

  ```
  $ psql postgres:///haushaltsbuch
  # ALTER SYSTEM SET log_destination = 'stderr';
  # ALTER SYSTEM SET log_statement = 'all';
  ```

Now apply the values with `pg_ctl reload -D /usr/local/var/postgres`. Logging messages will appear in the terminal window of the `postgres` process.

## Design Notes

* Input validation happens at two places: client-side (HTML form validation), where we are nice with the user, and at the database level, where we make sure only valid data enters the system. Right now there is no need for additional validation on the domain layer.
