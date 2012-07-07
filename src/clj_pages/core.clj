(ns clj-pages.core
  (:use clj-markdown.core
        clojure.java.io
        [clj-pages.views :only [index]]))

;; TODO recognize all defhtml in a ns and compile all of them
;; TODO create output directories if they don't exist

(defn path->filename
  "Returns the filename of an absolute path.
   ex. \"one/two/example.html\" => \"example.html\""
  [abs-path]
  (last (clojure.string/split abs-path #"/")))

(defn underscores->spaces [name]
  (clojure.string/replace name "_" " "))

(defn parse-path
  "Accepts an absolute path of the form: one/two/example_filename.md
   Returns a map:
     { :path \"_one/example_filename.md\"
       :target \"out/one/example_filename.html\"
       :filename \"example_filename.md\"
       :name \"example_filename\"
       :ext \".md\" }"
  [abs-path]
  (let [filename (path->filename abs-path)
        [filename name ext] (re-find #"(.+?)(\.[^.]*$|$)" filename)]
    {:path abs-path
     :href (str "posts/" name ".html")
     :filename filename
     :name name
     :ext ext}))

(defn starts-with? [string substring]
  (= (subs string 0 (count substring)) substring))

(def root-dir (.listFiles (java.io.File. ".")))
(def out-dir "out")
(def clusters (filter #(starts-with? (.getName %) "_") root-dir))
(def posts (first clusters))

(defn get-posts []
  (map (fn [post]
         (let [post (parse-path (.getPath post))]
           (assoc post :html (md->html (slurp (:path post))))))
       (.listFiles posts)))

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
      (spit (str out-dir "/posts/" (post-name post) ".html")
            (md->html (slurp post))))))

(defn compile-page
  "compiles page from hiccup to html"
  [name page]
  (spit (str out-dir "/" name ".html") (page (get-posts))))

(defn render-all
  "compiles all, add pages to compile them"
  []
  (compile-page "index" index)
  (compile-posts))

;;(defhtml post-item [link title]
;;         (link-to link title))

;;(map (link-to

#_(defn get-
  (let [posts (.listFiles (file "_posts/"))]
    (doseq [post posts]
      (map (str "blog/posts/" (post-name post) ".html") (post-name post)))))

