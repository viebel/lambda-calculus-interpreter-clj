(ns lambda-interpreter.core
  (:require [lambda-interpreter.utils :refer [car cdr cadr assq]]))

(declare eval-inner)
(declare lambda-apply)

(defn apply-expression [[f x] env]
  (lambda-apply (eval-inner f env)
                (eval-inner x env)))


(defn eval-inner [e env]
  (cond
    (symbol? e)     (or (cadr (assq e env)) e)
    (contains? '#{L λ} (car e))  (cons e env)
    :else           (apply-expression e env)))


; apply takes a function and an argument to a value
(defn lambda-apply [[[_ arg _ body] & env] x]
  (eval-inner body
              (cons (list arg x) env)))

(defn lambda-eval [e]
  (eval-inner e '()))

