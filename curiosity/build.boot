(def +version+ "0.1.0")

(set-env!
  :source-paths #{"src"}
  :dependencies '[[org.clojure/tools.logging   "0.4.0"]
                  [org.clojure/core.async      "0.4.474"]
                  [com.taoensso/nippy          "2.13.0"]

                  ;; boot clj
                  [boot/core                   "2.7.2"           :scope "provided"]
                  [adzerk/boot-logservice      "1.2.0"           :scope "test"]
                  [adzerk/bootlaces            "0.1.13"          :scope "test"]])

(require '[adzerk.bootlaces :refer :all]
         '[clojure.tools.logging :as log]
         '[adzerk.boot-logservice :as log-service])

(bootlaces! +version+)

(def log4b
  [:configuration
   [:appender {:name "STDOUT" :class "ch.qos.logback.core.ConsoleAppender"}
    [:encoder [:pattern "%d %-5level %logger{36} - %msg%n"]]]
   [:root {:level "TRACE"}
    [:appender-ref {:ref "STDOUT"}]]])

(deftask dev []
  (set-env! :source-paths #{"src" "dev"})
  (set-env! :resource-paths #{"resources" "dev-resources/camera"})
  (require '[curiosity.core :as curiosity]
           '[curiosity.dsn :as deep-space]
           '[mission])
  (alter-var-root #'log/*logger-factory*
                  (constantly (log-service/make-factory log4b)))
  (repl))

(task-options!
  push {:ensure-branch nil}
  pom {:project     'tolitius/curiosity
       :version     +version+
       :description "curiosity prototype: samples for jeeconf 2018 talk"
       :url         "https://github.com/tolitius/curiosity"
       :scm         {:url "https://github.com/tolitius/curiosity"}
       :license     {"Eclipse Public License"
                     "http://www.eclipse.org/legal/epl-v10.html"}})
