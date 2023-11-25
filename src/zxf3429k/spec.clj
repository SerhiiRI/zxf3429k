(ns zxf3429k.spec
  (:require [clojure.spec.alpha :as s]))

;; ----
;; Spec
;; ----

(def data
  [{:id 1, :band "Hentdrix", :songs ["Little Wing" "Voodoo Child"], :match-with-red-wine? true}
   {:id 2, :band "The Black Keys", :songs ["Lonely Boy" "Go" "Fever"], :match-with-red-wine? false}
   {:id 3, :band "Rival Sons", :songs ["Too Bad" "Sweet Life" "Open My Eyes"], :match-with-red-wine? false}
   {:id 4, :band "Beach Boys", :songs ["Kokomo" "Getcha Back"], :match-with-red-wine? true}
   {:id 5, :band "Dude with burger", :songs ["OH MY DAYUM!"], :match-with-red-wine? false}
   {:id 6, :band "Village People", :songs ["Macho Man" "Fireman"], :match-with-red-wine? false}])


;; --------
;; Solution
;; --------

(s/def ::id number?)
(s/def ::songs (s/+ string?))
(s/def ::match-with-red-wine? boolean?)
(s/def ::record (s/keys
                  :req-un [::id ::songs ::match-with-red-wine?]
                  :opt-un [::band]))
(s/def ::data (s/+ ::record))

(s/explain ::data data)


