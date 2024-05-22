(ns test1)

(def nome "Isaac")
(def idade "22")
(def numeros [1 2 3 4])
(println nome idade)

(defn saudacao
  "Saudação Clojure"
  [nome]
  (str "Bem vindo, " nome))

(saudacao nome)
(println (saudacao nome))
(println numeros)
