# funoop

> _initially created for [jeeconf 2018](https://jeeconf.com/)_

> _slides (made for Firefox): [gitpod.com/talks/jeeconf.2018](http://gitpod.com/talks/jeeconf.2018)_

Out of many strong JVM based languages Clojure and Java make a great couple.

Coming to Clojure from Spring/Java several years ago I really wished back then someone could explain functional programming and Clojure ecosystem to me from the Spring/Java point of view.

funoop bridges this gap by showing practical "How would you do X in Clojure" examples including:

* state management
  - application resources are usually mutable (network connections, files, databases, thread pools workers, etc..)
* application configuration
  - i.e. ENV variables, Zookeeper, Consul, etc.
* cross cutting concerns (AOP)
  - logging, exception handling, transaction management, security, profiling, etc.
* talking to data stores
  - SQL, NoSQL, AlmostSQL
* event processing
  - message brokers, HTTP endpoints, zeromq, etc.
* portability
  - an ability to communicate across languages: i.e. Java <==> C++ <==> Go <==> Rust, etc.
* async communication
  - NIO, go channels, threadpools, etc.

## License

Copyright © 2018 tolitius

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
