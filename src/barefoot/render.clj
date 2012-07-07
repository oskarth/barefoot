(ns barefoot.render
  (:use barefoot.core
        [bultitude.core :only [namespaces-on-classpath]]
        [clojure.java.io :only [file make-parents]]))

(defn render
  "outputs html into the given route (directory)"
  [route html-fn]
  (let [path (str out-dir "/" (namespace route) "/" (name route) ".html")]
    (make-parents path)
    (spit path (html-fn))))

(defn with-route [item route]
  (keyword (str (name route) "/" (:name item))))

(defn render-cluster [route html-fn]
  (doseq [item (get-cluster route)]
    (render (with-route item route) #(html-fn item))))

(defn render-all
  "compiles all, add pages to compile them"
  []
  (doseq [[route html-fn] @pages]
    (if (cluster? route)
      (render-cluster route html-fn)
      (render route html-fn))))

(defn load-pages [& dirs]
  (doseq [f (namespaces-on-classpath :classpath (map file dirs))]
    (require f)))

(defn -main [& args]
  (println "Compiling all...")
  (load-pages "src/")
  (render-all)
  (println "Done!"))
