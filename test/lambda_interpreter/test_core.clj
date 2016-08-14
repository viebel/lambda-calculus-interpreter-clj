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
         (fact "a function should eval to a list with itslef and the env"
               (eval-inner ?in ?env) => ?out)
         ?in ?env ?out
         '(L x . x) '() '((L x . x))
         '(L x . x) '((a 1)) '((L x . x) (a 1))
         '(L x . x) '((a 1) (b 2)) '((L x . x) (a 1) (b 2))
         )

       (tabular
         (fact "a function should eval to a list with itself" (lambda-eval ?in) => ?out)
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
         (fact "advanced: closure"
               (lambda-eval ?in) => ?out)
         ?in ?out
         '((L x . (L y . x)) a) '((L y . x) (x a))
         '((L m . (L f . (L n . (f n)))) (L f . (L n . n))) '((L f . (L n . (f n))) (m ((L f . (L n . n)))))
         '((L m . (L f . (L n . ((m f) n)))) (L f . (L n . n))) '((L f . (L n . ((m f) n))) (m ((L f . (L n .  n)))))
       ))


(facts "understanding"
       (eval-inner '(L m . (L f . (L n . (f n)))) '()) =>
                   '((L m . (L f . (L n . (f n)))))
       (eval-inner '(L f . (L n . n)) '()) =>
                   '((L f . (L n . n)))
       (apply-expression 
         '((L m . (L f . (L n . (f n)))) (L f . (L n . n)))
         '()) =>
       '((L f . (L n . (f n))) (m ((L f . (L n . n))))))
