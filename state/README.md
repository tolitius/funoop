# keeping app state in atoms

```
$ boot dev
```
```clojure
=> (pprint config)

{:db-spec
 {:subprotocol "postgresql",
  :subname "//localhost:5432/jeeconf-db",
  :user "clojure",
  :password "atom"},
 :cache
 {:redis
  {:host "localhost",
   :port "4242",
   :pool {:size 10000, :max-wait 60000}}}}
```

state is an empty map ("behind" the atom) before application starts:

```clojure
=> (type state.atom/app-state)
clojure.lang.Atom

=> (deref state.atom/app-state)
{}

=> @state.atom/app-state
{}
```

starting the app:

```clojure
=> (state.atom/start config)
2018-04-23 15:19:59,012 INFO  state.atom - connecting to {:subprotocol postgresql, :subname //localhost:5432/jeeconf-db, :user clojure, :password atom}
2018-04-23 15:19:59,014 INFO  state.atom - connecting to {:redis {:host localhost, :port 4242, :pool {:size 10000, :max-wait 60000}}}
{:db {:db-connection :object},
 :cache {:cache-connection :object}}
```

state is still an atom, but it now has connection objects ready to be used:

```
=> (type state.atom/app-state)
clojure.lang.Atom

=> @state.atom/app-state
{:db {:db-connection :object},
 :cache {:cache-connection :object}}
```

these objects are keys in a app-state map:

```
=> (@state.atom/app-state :db)
{:db-connection :object}
```

an example of a stateless function that uses this state:

```
=> (defn find-life [{:keys [db]} query]
     (println "looking for life inside" db ">" query))

#'/find-life
```

```
=> (find-life @state.atom/app-state "select water from mars;")
looking for life inside {:db-connection :object} > select water from mars;
```

## License

Copyright © 2018 tolitius

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
