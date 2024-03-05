(ns zxf3429k.interop)

;; -----------------
;; Simple Overriding
;; -----------------

;; 1) Implement methods with mutable mutable fields

(deftype User [login password])

(def z (->User "A" "B"))
(.setSecure z "Dummy")
(.getSecure z) ;;=> "Dummy"

;; 2) reimplement toString() method
(str User) ;;=> "#User{:user \"A\" :password \"D****\"}"

;; --------
;; Solution
;; --------

(definterface ISecure
  (setSecure [this]))
(deftype User [login ^:volatile-mutable password]
  ISecure
  (setSecure [this pass]
    (set! password pass))
  Object
  (toString [this]
    (let [[p & px] password]
      (clojure.pprint/cl-format nil "#User{:user \"~A\"~:[~; :password \"~c~v@{~c~:*~}\"~]}" login (some? p) p (count px) \*))))

(def z (->User "A" "B"))
(.setSecure z "Dummy")
(str z) ;; => "#User{:user \"A\" :password \"D****\"}"
