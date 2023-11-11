(ns core
  (:require [clojure.string :as string])
  (:require [clojure.spec.alpha :as s]))

;; ------------------------
;; Stage 1. Obtaining data
(defn parse-table [string-table]
  (map (fn [[k v]]
         (map second (re-seq #"<(?:td|th)>(.+?)\s*</(?:td|th)>" v)))
    (re-seq #"<tr>(.+?)</tr>" string-table)))

(comment
  (parse-table
    (slurp "test.html")))

(def data
 '(("num-1"  "bool-2"   "str-3"       "streats-4")
   ("1"      "true"     " _London"    "Baker,Oxford,Abbey")
   ("4"      " true "   "Saint_Malo"  "Louis Martin Avenue")
   ("2"      "false"    "Kalush "     "Broadway,Washington")
   ("3"      "  false"  "Florence"    "Tornabuoni,Calzaiuoli")))
;; => ({:num-1 "1", :bool-2 "true", :str-3 " _London", :streats-4 "Baker,Oxford,Abbey"}... )



(let [[h & r] 
      '(("num-1"  "bool-2"   "str-3"       "streats-4")
        ("1"      "true"     " _London"    "Baker,Oxford,Abbey")
        ("4"      " true "   "Saint_Malo"  "Louis Martin Avenue")
        ("2"      "false"    "Kalush "     "Broadway,Washington")
        ("3"      "  false"  "Florence"    "Tornabuoni,Calzaiuoli"))]
  (->>
    (for [e r]
      (into {}
        (map #(vector (keyword %1) %2)
          h
          e)))
    (map (fn [e]
           (-> e
             (update :num-1 parse-long)
             (update :bool-2 (comp parse-boolean string/trim))
             (update :str-3 string/trim)
             (update :streats-4 string/split #","))))
    (sort-by :num-1)
    ))





;; -------------------
;; Stage 2. Valid data
(defn valid [data]
  ;; // use clojure.alpha.spec for validation
  )


;; -----------------------------------
;; Stage 3. Implement custom DataFrame 
;; 
;;  (def df
;;   (DataFrame.
;;    ["numeric-1" "boolean-2" "s..."]
;;    [[1,2,3,4]
;;     [true,false...]
;;     [..,..,..])
;;

(defrecord DataFrame [colnames columns])


;; ----------------------------------
;; Stage 4. Convert DataFrame back to
;; HTML table
;; (.toHTML df)


;; --------------------
;; Test all 
(->>
  (slurp "test.html")
  (parse-table)
  ;; parsedata
  ;; validate existing data
  ;; output to file
  ;; (spit "output.html" (.toHTML df))
  )


