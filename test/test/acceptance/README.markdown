# Acceptance Tests

Uses [ruby bindings](https://github.com/SeleniumHQ/selenium/wiki/Ruby-Bindings) to [Selenium](https://seleniumhq.github.io)'s [https://sites.google.com/a/chromium.org/chromedriver/](chrome-driver).

See also the [cheat sheet](https://gist.github.com/kenrett/7553278).

## Installation

```
bundle install
brew bundle # or use an alternative method to install chrome-driver
```

## Running Tests

```
bundle exec rake
```

By default, the acceptance tests will run against a local Liberty server. If you want to run against another instance, configure the `HAUSHALTSBUCH_URL` environment variable, for instance:

```
HAUSHALTSBUCH_URL=http://haushaltsbuch.eu-gb.mybluemix.net bundle exec rake
```
