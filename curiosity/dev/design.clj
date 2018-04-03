;; navigation commands are sent to the Deep Space Network
(defn publish-events [dsn events])

;; navigation commands are received by the rover
(defn consume-events [dsn])

;; navigation commands are passed to the command center
(defn parse-commands [events])

;; navigation commands are split and sent in sequence to navigator
(defn navigate [directions])

;; most likely low level engine commands are sent to move the rover
(defn move [movement])

;; (once rover arrives to the destination) capture commands are sent to cameras
(defn capture [instruments])

;; image events are sent back to the rover
(defn save [store events])

;; image events are sent back to the Deep Space Network
;; might reuse (defn publish-events [dsn events])

;; image events are received by RSVP
;; might reuse (defn consume-events [dsn])
