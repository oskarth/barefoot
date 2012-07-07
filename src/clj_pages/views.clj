(ns clj-pages.views
  (:use hiccup.core
        hiccup.def
        clj-pages.core))

(defhtml header []
  [:h1 "this is my header"])

(defhtml post-item [post]
  [:li [:a {:href (:href post)} (:name post)]])

(defhtml posts-list [posts]
  [:ul
   (map post-item posts)])

(defpage :posts [post]
  (header)
  (:html post))

(defpage :index []
  (posts-list (get-cluster :posts))
  [:h1 "x"]
  (header))
