(ns clj-pages.render
  (:use [clj-pages.core :only [render-all]]))

(defn -main
  [& args]
  (println "Compiling all...")
  (render-all)
  (println "Done!"))
