# lambda-calculus-interpreter-clj
Lambda Calculus interpreter in clojure


### Automatic tests

The testable parts of the code are written in a portable way. It's simpler to test in clojure than in cljs.

```bash
lein repl
user=> (use 'midje.repl)
user=> (autotest)
```
