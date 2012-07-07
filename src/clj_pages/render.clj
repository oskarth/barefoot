(ns clj-pages.render
  (:use clj-pages.core
        [bultitude.core :only [namespaces-on-classpath]]
        [clojure.java.io :only [file make-parents]]))

#_(defn compile-posts
  "compiles all .md files in _posts directory into html files"
  []
  (let [posts (.listFiles (file "_posts/"))]
    (doseq [post posts]
      (spit (str out-dir "/posts/" (post-name post) ".html")
            (md->html (slurp post))))))

(defn render
  "outputs html into the given route (directory)"
  [route html-fn]
  (let [dir (str out-dir "/" (namespace route))]
    (make-parents dir)
    (spit dir (str (name route) ".html") (html-fn))))

;; => out/ 

(defn render-cluster [route html-fn]
  (map (fn [item]
         (let [route (keyword (str (name route) "/" (:name item)))]
           (render route #(html-fn item))))
       (get-cluster route)))

(defn render-all
  "compiles all, add pages to compile them"
  []
  (println "pages " @pages)
  (doseq [[route html-fn] @pages]
    (if (cluster? route)
      (render-cluster route html-fn)
      (render route html-fn))))

(defn load-pages [& dirs]
  (doseq [f (namespaces-on-classpath :classpath (map file dirs))]
    (println "f is   " f)
    (require f)))


(defn -main [& args]
  (println "Compiling all...")
  (load-pages "src/clj-pages/")
  (render-all)
  (println "Done!"))
