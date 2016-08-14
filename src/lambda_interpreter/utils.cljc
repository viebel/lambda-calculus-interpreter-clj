(ns lambda-interpreter.utils)

(def car first)
(def cdr rest)

(def cadr (comp car cdr))
(def cddr (comp cdr cdr))
(def cdddr (comp cdr cdr cdr))

(defn assq [x lst]
  (first (filter 
           (fn [[k v]]
             (= k x))
           lst)))


