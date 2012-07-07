(ns clj-pages.views
  (:use hiccup.core
        hiccup.def
        clj-pages.core))

(defhtml header []
  [:div {:style "background:blue;"}
   [:h1 "This... is... CLJ-PAGES!!!!!11"]])

(defhtml post-item [post]
  [:li [:a {:href (:href post)} (:name post)]])

(defhtml posts-list [posts]
  [:ul
   (map post-item posts)])

(defpage :posts [post]
  (header)
  (:html post))

(defpage :static [page]
  (header)
  [:div {:style "background:yellow;"}
   [:h3 "aboot us"]
   (:html page)])

(defpage :index []
  (posts-list (get-cluster :posts))
  [:h1 "x"]
  (header))
