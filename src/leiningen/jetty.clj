(ns leiningen.jetty
  "Jetty server plugin"
  (:require [leiningen.compile :as compile])
  (:import [org.apache.commons.dbcp BasicDataSource]
           [org.mortbay.jetty Server]
           [org.mortbay.jetty.plus.naming Resource]
           [org.mortbay.jetty.webapp WebAppContext]))

(def ^{:private true}
  configurations
  ["org.mortbay.jetty.webapp.WebInfConfiguration"
   "org.mortbay.jetty.plus.webapp.EnvConfiguration"
   "org.mortbay.jetty.plus.webapp.Configuration"
   "org.mortbay.jetty.webapp.JettyWebXmlConfiguration"
   "org.mortbay.jetty.webapp.TagLibConfiguration"])

(defn- web-app-context [project]
  (let [config (:jetty project)]
    (doto (WebAppContext.)
      (.setConfigurationClasses (into-array String configurations))
      (.setContextPath (:context-path config "/"))
      (.setResourceBase (get-in project [:war :web-content] "src/html"))
      (.setDescriptor (get-in project [:war :webxml] "src/web.xml"))
      (.setExtraClasspath (:compile-path project "classes"))
      (.setParentLoaderPriority true))))

(defn- datasource [config]
  (doto (BasicDataSource.)
    (.setDriverClassName (:driver-class-name config))
    (.setUrl (:url config))
    (.setUsername (:username config))
    (.setPassword (:password config))))

;; TODO: support env-entries, resource-refs and resource-env-refs
(defn- configure
  "configure the web-app-context with resources defined in the project"
  [ctx project]
  (when-let [config (get-in project [:jetty :datasource])]
    (println "configuring datasource:" (:name config))
    (Resource. ctx (:name config) (datasource config))))

(defn jetty
  "Start jetty on specified port (default: 8080)"
  [project & [port]]
  (compile/compile project)
  (let [port* (or port 8080)]
    (doto (Server. port*)
      (.addHandler
       (doto (web-app-context project)
         (configure project)))
      (.start)
      (.join))))
