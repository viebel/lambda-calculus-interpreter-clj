(ns lambda-interpreter.test-core
  (:use [midje.sweet]
        [lambda-interpreter.core]))

(facts "eval symbols"
       (tabular
         (fact "eval a symbol without env" (lambda-eval ?in) => ?in)
         ?in 
         'z 
         'zz 
         'abc) 
       (tabular
         (fact "eval a symbol with env" (eval-inner ?in ?env) => ?out)
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

(facts "apply functions"
       (tabular
         (fact "a function should apply arguments to its body"
               (lambda-eval ?in) => ?out)
         ?in ?out
         '((L x . x) (L x . x)) '((L x . x))
         '((L x . x) a) 'a
         '(((L x . (L y . x)) a) b) 'a)

       (tabular
         (fact "a function defined with λ should apply arguments to its body"
               (lambda-eval ?in) => ?out)
         ?in ?out
         '((λ x . x) (λ x . x)) '((λ x . x))
         '((λ x . x) a) 'a
         '(((λ x . (λ y . x)) a) b) 'a)

       (tabular
         (fact "advanced"
               (lambda-eval ?in) => ?out)
         ?in ?out
         '((L x . x) (L x . x)) '((L x . x))
         '((L x . x) a) 'a
         '(((L x . (L y . x)) a) b) 'a)
       )
