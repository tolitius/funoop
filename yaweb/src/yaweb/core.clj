(ns yaweb.core)

(defn routes [{:keys [route params cookies]}]
  (case route
    {:GET "/"} {:status 200 :response {:greetings (or params "stranger")}}
    {:POST "/login"} {:status 200 :response {:welcome params :session-id cookies}}
    {:status 404 :can't-find route}))

(defn wrap-cookies [handler]
  (fn [{:keys [secrets] :as request}]
    (-> (assoc request :cookies secrets)   ;; pretending to parse cookies from request
        handler)))

(defn wrap-params [handler]
  (fn [{:keys [args] :as request}]
    (-> (assoc request :params args)       ;; pretend to parse (POST/GET) params from request
        handler)))

(defn wrap-exception-handling [handler]
  (fn [request]
    (try
      (handler request)
      (catch Exception e
        {:status 500 :body (str "oops.. : " e)}))))

(defn web-app [routes]
  (-> routes
      wrap-cookies
      wrap-params
      wrap-exception-handling))
