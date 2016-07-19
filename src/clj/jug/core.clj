(ns jug.core
    (:require [crypto.random :as random]
              [compojure.core :refer :all]
              [compojure.route :refer [not-found]]
              [ring.util.response :refer [resource-response]]
              [ring.middleware.resource :refer [wrap-resource]]
              [ring.middleware.session :refer [wrap-session]]
              [ring.middleware.session.cookie :refer [cookie-store]]
              [ring.middleware.stacktrace :refer [wrap-stacktrace]]
              [ring.logger :refer [wrap-with-logger]]

              [jug.middleware.ssr :refer [wrap-ssr-routes]])
    (:import (org.joda.time DateTime)))

;; (defn api-handler [request]
;;   (str (:query-string request)))

;; (defn api-handler [request]
;;   (str (DateTime.)))

;; (defn api-handler [request]
;;   (.getAsString (.monthOfYear (DateTime.))))

;; (defn api-handler [request]
;;   (.. (DateTime.)
;;       (monthOfYear)
;;       (getAsString)))



(defn api-handler [request]
  (let [date (doto (java.util.Date.)
               (.setYear 2012)
               (.setMonth 10)
               (.setDate 2))]
    (str date)))

(defroutes middleware-routes
  (GET "/api" request api-handler)
  (not-found (resource-response "index.html" {:root "public"})))

(def app
  (-> middleware-routes
      (wrap-resource "public")
      (wrap-ssr-routes)
      wrap-stacktrace
      wrap-with-logger))

