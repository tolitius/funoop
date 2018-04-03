(ns curiosity.dsn
  "this is us pretending to be a Deep Space Network library"
  (:require [clojure.core.async :as async :refer [>! <!]]
            [clojure.tools.logging :as log]))

(def ^:private deep-space (atom {}))

(defn connect [{:keys [station]}]

  (when-not station
    (throw (ex-info "in order to connect to Deep Space Network, please provide a station: i.e. {:station \"dsn://address\"}"
                    {:cause :missing-station})))

  (log/info "Deep Space Network: accepting connection to" station "station")
  (if-let [channel (@deep-space station)]
    channel
    (let [conn (async/chan)]
      (swap! deep-space assoc station conn)
      conn)))

(defn send-event [chan event]
  (async/go (>! chan event)))

(defn consume-events [chan handle]
  (let [stop (async/chan)]
    (async/go-loop []
                   (async/alt!
                     stop ([_]
                           (log/info "stopping Deep Space Network consumer"))
                     chan ([event]
                           (handle event)
                           (recur))))
    {:stop stop}))

(defn stop-consumer [{:keys [stop]}]
  (async/go (>! stop :now)))
