(ns clj-pages.views
  (:use hiccup.core
        hiccup.def))

(defhtml header []
         [:h1 "this is my header"])

(defhtml index []
         (header)
         [:h2 "Yes it is"]
         [:h3 "Really!"])
