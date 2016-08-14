# lambda-calculus-interpreter-clj
Lambda Calculus interpreter in clojure


Inspired by http://matt.might.net/articles/implementing-a-programming-language/

### Automatic tests

The testable parts of the code are written in a portable way. It's simpler to test in clojure than in cljs.

```bash
lein repl
user=> (use 'midje.repl)
user=> (autotest)
```

