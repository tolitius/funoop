(ns state.atom
  (:require [clojure.tools.logging :as log]))

(def config {:db-spec {:subprotocol "postgresql"
                       :subname "//localhost:5432/jeeconf-db"
                       :user "clojure"
                       :password "atom"}
             :cache {:redis {:host "localhost"
                             :port "4242"
                             :pool {:size 10000
                                    :max-wait 60000}}}})

(def app-state (atom {}))

(defn db-connect [{:keys [db-spec]}]
  (log/info "connecting to" db-spec)
  {:db-connection :object})

(defn cache-connect [{:keys [cache]}]
  (log/info "connecting to" cache)
  {:cache-connection :object})

(defn start [conf]
  (reset! app-state {:db (db-connect conf)
                     :cache (cache-connect conf)}))

