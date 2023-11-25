(ns zxf3429k.base
  (:require [clojure.string :as string]))

;; ----
;; Base
;; ----

(def data
  '(("id" "band"             "songs"                             "match-with-red-wine?")
    ("1"  " Hentdrix"        "Little Wing , Voodoo Child"        "true")
    ("4"  "Beach Boys "      "Kokomo, Getcha Back"               " true ")
    ("6"  "Village People"   "Macho Man; Fireman"                "")
    ("2"  " The Black Keys"  "Lonely Boy, Go, Fever"             "false")
    ("5"  "Dude with burger" "OH MY DAYUM!"                      " FALSE ")
    ("3"  "Rival Sons"       "Too Bad; Sweet Life; Open My Eyes" "  false")))
;; Let transform into Vector of key-value Map's
;;    [{:id "1", :band " Hentdrix", :songs "Little Wing , Voodoo Child", :does ....}
;;     {:id "4", :band "Beach Boys", :songs "Kokomo, Getcha Back", :does-it...}
;;     ...]


;; --------
;; Solution
;; --------

(let [[h & r] data
      h (map keyword h)]
  (->> r
    (map #(zipmap h %))
    (map #(-> %
            (update :id parse-long)
            (update :band string/trim)
            (update :songs string/split #"\s*[,;]\s*")
            (update :match-with-red-wine? (comp boolean parse-boolean string/lower-case string/trim))))
    (sort-by :id)
    (vec)))

