(ns zxf3429k.macros
  (:require [clojure.string :as string]))

;; -------------------
;; Message Constructor
;; -------------------

(def z {:a "" :x nil})

(message-constructor "Unvalid `z` struct"
  (nil? (:x z))          "value `:x` has 'nil' value"
  (string/empty? (:a z)) "value `:a` empty"
  (map? z)               "is not 'Map'")
;; RETURN [<valid?>, <String | nil> ]
;; EXAMPLE =>
;;   [false, "Unvalid `z` struct: value `:x` has 'nil' value; value `:a` empty."]

;; ---------------
;; Back Lisp 'Let'
;; ---------------

(blet
  (+ a b)
  ((a 1)
   (b 2)))

;; should be transformed into
;; usual let block

(let [a 1
      b 2]
  (+ a b))

