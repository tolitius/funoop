(ns curiosity.core
  (:require [clojure.tools.logging :as log]
            [clojure.java.io :as io]
            [taoensso.nippy :as nippy]
            [curiosity.dsn :as deep-space])
  (:import javax.xml.bind.DatatypeConverter
           java.io.File))

;; navigation commands are sent to the Deep Space Network
(defn publish-events [msg dsn events]
  (log/info msg)
  (deep-space/send-event dsn
                         (nippy/freeze events)))

;; navigation commands are received by the rover
;; will use (deep-space/consume-events dsn process) for now

;; navigation commands are passed to the command center
(defn parse-commands [events]
  (log/info "Mars rover: processing events from Earth")
  (->> (nippy/thaw events)
       (into (sorted-map-by <))))

;; most likely low level engine commands are sent to move the rover
(defn move [[order movement]]
  (log/info "Mars rover:" order "=>" movement))

;; navigation commands are split and sent in sequence to navigator
(defn navigate [directions]
  (mapv move directions))

;; (once rover arrives to the destination) capture commands are sent to cameras
(defn capture [cameras]
  (mapv (fn [[id cam-type]]
          (log/info "Mars rover: capturing image with" cam-type)
          [cam-type (-> id str io/resource slurp)])              ;; pretending to capture images
        cameras))

;; image events are sent back to the rover
(defn save [store events]
  (log/info "Mars rover: storing images from" (map first events) "to" store)
  events)

;; image events are sent back to the Deep Space Network
;; might reuse (defn publish-events [dsn events])

;; image events are received by RSVP
;; will use (deep-space/consume-events dsn process) to receive them
;; and then we would store them on the Earth side
(defn store-images [path events]
  (let [images (nippy/thaw events)]
    (log/info "Earth: storing data from" (map first images)
              "rover cameras on the Jet Propulsion Laboratory servers")
    (mapv (fn [[id image]]
            (-> (DatatypeConverter/parseBase64Binary image)
                (io/copy (File. (str path "/" id)))))
          images)))
