(ns lambda-interpreter.test-core
  (:use [midje.sweet]
        [lambda-interpreter.core]))

(facts "eval-inner symbols"
       (tabular
         (fact "eval a symbol" (eval-inner ?in ?env) => ?out)
         ?in ?env ?out
         'z '((z 2) (z 1)) 2
         'z '((a 2) (z 1)) 1
         'z '((z 1)) 1
         'z '() 'z))

(facts "eval functions"
       (tabular
         (fact "a function should eval to itslef with the env"
               (eval-inner ?in ?env) => ?out)
         ?in ?env ?out
         '(L x . x) '() '((L x . x))
         '(L x . x) '((a 1)) '((L x . x) (a 1))
         '(L x . x) '((a 1) (b 2)) '((L x . x) (a 1) (b 2))
         )

       (tabular
         (fact "a function should eval to itself with no env" (lambda-eval ?in) => ?out)
         ?in ?out
         '(L x . x) '((L x . x))))
