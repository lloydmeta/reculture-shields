# Re:culture shields

Simple shield generator for Re:Culture. 

Uses the awesome [Shields.io](http://shields.io/) for actual shield generation.

## In terminal

```
$ sbt run
```

## In Browser

Replace 17 with id of your choice

`http://localhost:9000/badge/17.svg`

## Deploy to Heroku

SBT-Heroku has been set up, so we can compile locally and upload the Jar to
Heroku.

```
$ sbt stage deployHeroku
```
  
`herokuApp` can be passed as an environment variable to set where you want to deploy. By default,
we deploy to an app called `culture-badge-test`.