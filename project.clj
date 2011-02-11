(defproject com.mefesto/lein-jetty "1.0.0-SNAPSHOT"
  :description "lein-jetty"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [commons-dbcp/commons-dbcp "1.4"]
                 [javax.servlet/servlet-api "2.5"]
                 [org.mortbay.jetty/jetty "6.1.26"]
                 [org.mortbay.jetty/jsp-2.1-jetty "6.1.26"]
                 [org.mortbay.jetty/jetty-naming "6.1.26"]
                 [org.mortbay.jetty/jetty-plus "6.1.26"]]
  :dev-dependencies [[swank-clojure "1.3.0-SNAPSHOT"]]
  :eval-in-leiningen true)
