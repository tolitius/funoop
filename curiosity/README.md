# curiosity

```clojure
$ boot dev

=> (def config (mission/load-config "resources/config.edn"))

=> (def directions {1 "go forward 100 meters"
                    2 "turn right 42 degrees"
                    3 "go forward 28.2 meters"})
```

```clojure
=> (pprint config)
{:rover
 {:station "dsn://reconnaissance",
  :store "ssd://mission-keeper",
  :cameras {1 "mast-cam", 2 "chem-cam", 3 "nav-cam", 4 "haz-cam"}},
 :jpl {:station "dsn://goldstone", :server "/jpl/mission/report/"}}
```

```clojure
=> (def find-life (mission/execute config directions))

2018-03-27 15:03:30,358 INFO  curiosity.dsn - Deep Space Network: accepting connection to dsn://goldstone station
2018-03-27 15:03:30,362 INFO  curiosity.dsn - Deep Space Network: accepting connection to dsn://reconnaissance station
2018-03-27 15:03:30,364 INFO  curiosity.core - Earth: sending events to Mars rover
2018-03-27 15:03:30,374 INFO  curiosity.core - Mars rover: processing events from Earth
2018-03-27 15:03:30,379 INFO  curiosity.core - Mars rover: 1 => go forward 100 meters
2018-03-27 15:03:30,379 INFO  curiosity.core - Mars rover: 2 => turn right 42 degrees
2018-03-27 15:03:30,379 INFO  curiosity.core - Mars rover: 3 => go forward 28.2 meters
2018-03-27 15:03:30,380 INFO  curiosity.core - Mars rover: capturing image with mast-cam
2018-03-27 15:03:30,382 INFO  curiosity.core - Mars rover: capturing image with chem-cam
2018-03-27 15:03:30,385 INFO  curiosity.core - Mars rover: capturing image with nav-cam
2018-03-27 15:03:30,387 INFO  curiosity.core - Mars rover: capturing image with haz-cam
2018-03-27 15:03:30,391 INFO  curiosity.core - Mars rover: storing images from (mast-cam chem-cam nav-cam haz-cam) to ssd://mission-keeper
2018-03-27 15:03:30,391 INFO  curiosity.core - Mars rover: sending report back to Earth
2018-03-27 15:03:30,410 INFO  curiosity.core - Earth: storing data from (mast-cam chem-cam nav-cam haz-cam) rover cameras on the Jet Propulsion Laboratory servers
```

```clojure
boot.user=> ((:shutdown find-life))
2018-03-27 15:27:06,361 INFO  curiosity.dsn - stopping Deep Space Network consumer
2018-03-27 15:27:06,361 INFO  curiosity.dsn - stopping Deep Space Network consumer
```
