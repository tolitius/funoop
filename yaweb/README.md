# yaweb: yet another web framework

given a set of routes:

```clojure
(defn routes [{:keys [route params cookies]}]
  (case route
    {:GET "/"} {:status 200 :response {:greetings (or params "stranger")}}
    {:POST "/login"} {:status 200 :response {:welcome params :session-id cookies}}
    {:status 404 :can't-find route}))
```

and some custom middleware:

```clojure
(defn web-app [routes]
  (-> routes
      wrap-cookies
      wrap-params))
```

out framework does wonders:

```clojure
$ boot dev

=> (def www (web-app routes))

=> (www {:route {:GET "/"}})
{:status 200, :response {:greetings "stranger"}}

=> (www {:route {:GET "/"} :args {:name "Thor"}})
{:status 200, :response {:greetings {:name "Thor"}}}

=> (www {:route {:GET "/login"} :args {:name "Thor"}})
{:status 404, :can't-find {:GET "/login"}}

=> (www {:route {:POST "/login"} :args {:name "Thor"}})
{:status 200, :response {:welcome {:name "Thor"}, :session-id nil}}

=> (www {:route {:POST "/login"} :args {:name "Thor"} :secrets 42})
{:status 200, :response {:welcome {:name "Thor"}, :session-id 42}}
```

but what if there is an exception:

```clojure
=> (www :bad-request)

java.lang.ClassCastException: clojure.lang.Keyword cannot be cast to clojure.lang.Associative
```

oops, server is down.. if only we had something like AOP to address this cross cutting concern and add exception handling to all web requests:

```clojure
(defn wrap-exception-handling [handler]
  (fn [request]
    (try
      (handler request)
      (catch Exception e
        {:status 500 :body (str "oops.. : " e)}))))
```

oh, just a function? niice.

```clojure
(defn web-app [routes]
  (-> routes
      wrap-cookies
      wrap-params
      wrap-exception-handling))
```

interesting, we can just add it to the middleware pipeline.. let see if it does the work:

```clojure
=> (def www (web-app routes))

=> (www {:route {:GET "/"}})
{:status 200, :response {:greetings "stranger"}}

=> (www :bad-request)
{:status 500, :body "oops.. : java.lang.ClassCastException: clojure.lang.Keyword cannot be cast to clojure.lang.Associative"}
```

niice.
