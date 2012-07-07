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

(defhtml index [posts]
  (header)
  (posts-list posts)
  [:h2 "Yes it is"]
  [:h3 "Really!"])

(defpage :index []
  (posts-list (get-cluster :posts))
  [:h1 "x"]
  (header))