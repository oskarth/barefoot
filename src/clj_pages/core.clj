(ns clj-pages.core
  (:use clj-markdown.core
        clojure.java.io
        clj-pages.views))

;; TODO recognize all defhtml in a ns and compile all of them

(defn post-name
  "returns a posts name; foo.md => foo"
  [f]
  (let [n (.getName f)]
    (nth (re-find #"(.+?)(\.[^.]*$|$)" n) 1)))

(defn compile-posts
  "compiles all .md files in _posts directory into html files"
  []
  (let [posts (.listFiles (file "_posts/"))]
    (doseq [post posts]
      (spit (str "blog/posts/" (post-name post) ".html")
            (md->html (slurp post))))))

(defn compile-page
  "compiles page from hiccup to html"
  [name page]
  (spit (str "blog/" name ".html") (page)))

(defn compile-all
  "compiles all, add pages to compile them"
  []
  (compile-page "index" index)
  (compile-posts))

(defn -main
  "compiles all posts when doing lein run"
  [& args]
  (println "Compiling all...")
  (compile-all)
  (println "Done!"))

;;(defhtml post-item [link title]
;;         (link-to link title)) 

;;(map (link-to

#_(defn get-
  (let [posts (.listFiles (file "_posts/"))]
    (doseq [post posts]
      (map (str "blog/posts/" (post-name post) ".html") (post-name post)))))
 
