(ns mission
  (:require [clojure.edn :as edn]
            [curiosity.core :as curiosity]
            [curiosity.dsn :as deep-space]))

;; later split to 2 different functions
;; coupled here to illustrace the full mission
(defn move-and-report [dsn {:keys [store cameras]} directions]
  (-> directions
      curiosity/parse-commands
      curiosity/navigate)
  (->> cameras
       curiosity/capture
       (curiosity/save store)
       (curiosity/publish-events "Mars rover: sending report back to Earth" dsn)))

(defn execute [{:keys [jpl rover]} commands]
  (let [earth-conn (deep-space/connect jpl)
        rover-conn (deep-space/connect rover)
        earth-consumer (deep-space/consume-events
                         earth-conn
                         (partial curiosity/store-images (:server jpl)))
        rover-consumer (deep-space/consume-events
                         rover-conn
                         (partial move-and-report earth-conn rover))]
    (curiosity/publish-events "Earth: sending events to Mars rover"
                              rover-conn
                              commands)
    (Thread/sleep 100) ;; let mission take some time for illustrative/demo purposes
    {:shutdown #(mapv deep-space/stop-consumer [rover-consumer earth-consumer])}))

(defn load-config [path]
  (edn/read-string (slurp path)))
