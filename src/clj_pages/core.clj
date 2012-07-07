(ns clj-pages.core
  (:use clj-markdown.core
        clojure.java.io
        [hiccup.def :only [defhtml]]))

;; TODO recognize all defhtml in a ns and compile all of them
;; TODO create output directories if they don't exist
;; TODO add clj-yaml support in posts a la jekyll


;; http://dev.clojure.org/display/community/Google+Summer+of+Code+2012#GoogleSummerofCode2012-Noirstaticsitegenerator
;; jekyll

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

;; utils

(defn starts-with? [string substring]
  (= (subs string 0 (count substring)) substring))

(defn dir->kw [dir]
  (-> dir .getName (subs 1) keyword))

(def root-dir (.listFiles (java.io.File. ".")))
(def out-dir "out")
(def cluster-dirs (filter #(starts-with? (.getName %) "_") root-dir))
(def cluster-kws (map dir->kw cluster-dirs))

(defn cluster?
  "returns true of k is a cluster key"
  [k]
  (some #{k} cluster-kws))

(defn get-cluster-items [cluster]
  (map (fn [item]
         (let [item (parse-path (.getPath item))]
           (assoc item :html (md->html (slurp (:path item))))))
       (.listFiles cluster)))

(def clusters
  (reduce
   (fn [coll dir] (assoc coll (dir->kw dir) (get-cluster-items dir)))
   {}
   cluster-dirs))

(defn post-name
  "returns a posts name; foo.md => foo"
  [f]
  (let [n (.getName f)]
    (nth (re-find #"(.+?)(\.[^.]*$|$)" n) 1)))

(defn parse-args [[route args-list & body]]
  {:route route :args-list args-list :body body})

(def pages (atom {}))

(defmacro defpage
  "associates a route with a function that generates html"
  [& args]
  (let [{:keys [route args-list body]} (parse-args args)]
    `(swap! pages assoc ~route (defhtml ~(gensym) ~args-list ~@body))))

(def get-cluster (partial get clusters))
