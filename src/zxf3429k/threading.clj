(ns zxf3429k.threading)

;; ----
;; Task
;; ----

;; Let assume we have a Two boxes "A" "B"
;; In box loop we decrementing one and in-
;; crementiog another:
;;
;;  A  B
;; -----
;; 10  0 =SUM= 10
;;  9  1 =SUM= 10
;;  8  2 ...
;;  7  3
;;  ....
;;

(time
  (let [size 10
        box-a (atom size)
        box-b (atom 0)
        result (atom [])]
    (doall
      ;; *(map deref)
      (map
        ;; *do -> future
        #(do
           (print (format "%d<" % ))
           ;; ----
           (Thread/sleep 200)
           (swap! box-a dec)
           (swap! box-b inc)
           (swap! result conj {:thread % :sum (+ @box-a @box-b) :a @box-a :b @box-b})
           ;; ----
           (print (format "a=%d,b=%d" @box-a @box-b))
           (print (format ">%d\n" %)))
        (range size)))
    (clojure.pprint/print-table [:thread :sum :a] @result)
    (println (format "\tA = %d\n\tB = %d" @box-a @box-b ))))

;; After invoking map body in thread, the
;; incrementing and decrementing maked in separed
;; threads. But value 'A & 'B at point are uncontrolled
;; so if we going SUM it, they not return 10.

;; ------------------
;; What we have to do?
;;  We need to keep our concurency efficient, but
;;  always have to controll over the inc/dec process

;; ------------------
;; Motivation?
;;  Clojure powered with STM concept with an
;;  several mutable types. This task is attempt
;;  to figure out how we can take control over the
;;  so simple case.

;; --------
;; Solution
;; --------

(time
  (let [result (java.util.concurrent.LinkedBlockingQueue.)
        size 10
        box-a (ref size)
        box-b (ref 0)]
    (doall
      (map deref
        (map
          #(future
             (clojure.pprint/cl-format *out* "~D<" % )
             (Thread/sleep 200)
             (dosync
               (alter box-a dec)
               (alter box-b inc)
               (clojure.pprint/cl-format *out* "~D+~D=~D" @box-a @box-b (+ @box-a @box-b))
               (.put result {:thread % :sum (+ @box-a @box-b) :a @box-a :b @box-b}))
             (clojure.pprint/cl-format *out* ">~D ~%" % ))
          (range size))))
    (clojure.pprint/print-table [:thread :formula]
      (map (fn [{:keys [thread sum a b] :as m}]
             (-> m
               (dissoc :a :b)
               (assoc :formula
                 (clojure.pprint/cl-format nil "~2D +~2D = ~2D" a b sum))))
        (seq result)))
    (clojure.pprint/cl-format *out* "~2TA = ~D~%~2TB = ~D~%~2TT:=> " @box-a @box-b)))
