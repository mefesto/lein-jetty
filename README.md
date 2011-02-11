# lein-jetty

A leiningen plugin that launches a jetty servlet container with support for jndi datasources.

## Usage

This project should be using in conjunction with `leiningen-war`.

First, include the following dev-dependencies in your project.clj:

    :dev-dependencies [[com.mefesto/lein-jetty "1.0.0-SNAPSHOT"]
                       [uk.org.alienscience/leiningen-war "0.0.12"]
                       [postgresql/postgresql "8.4-702.jdbc4"]]

Substitute the postgresql dependency for which ever database you're using.

Next, configure some of `lein-jetty`'s options in your `project.clj`:

    :jetty {:context-path "/testapp"
            :datasource {:name "jdbc/DefaultDS" ; be sure this is in your web.xml
                         :driver-class-name "org.postgresql.Driver"
                         :url "jdbc:postgresql://localhost/testapp"
                         :username "testapp"
                         :password "testapp"}}

Declare your datasource in your `web.xml` file:

    <web-app>
      ...
      <resource-ref>
        <res-ref-name>jdbc/DefaultDS</res-ref-name>
        <res-type>javax.sql.DataSource</res-type>
        <res-auth>Container</res-auth>
      </resource-ref>
      ...
    </web-app>

Now, assuming your web project is setup according to [leiningen-war docs](https://github.com/alienscience/leiningen-war) you should be able to launch jetty:

    $ lein jetty

For better or worse jsp support is included. :-\

## TODO

Expand this to support other jndi resources such as:

  * env-entry
  * resource-ref
  * resource-env-ref
  * others?

## License

Copyright (C) 2010 Allen Johnson

Distributed under the Eclipse Public License, the same as Clojure.
