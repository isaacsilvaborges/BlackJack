(ns game
  (:require [card-ascii-art.core :as card]))

;A, 2, 3, 4, ...., 10, J, Q, K

(defn nova-carta []                                         ;Função de nova carta
  "Gera um número de carta de 1-13"
  (inc (rand-int 13)))                                      ;Gera uma carta de 1 a 13

;;calcula as regras dos pontos-cartas
;;J, Q, K valem 10, não 11, 12, 13
;;A vale sempre 11, a não ser que estoure, então vale 1

(defn JQK->10 [carta]
  (if (> carta 10) 10 carta))                               ;Se for maior que 10, retorna 10, senão retorna carta

(defn A->11 [carta]                                         ;Se o valor de A for 1, subistitui por 11
  (if (= carta 1) 11 carta))

(defn pontos-cartas [cartas]                                ;Calculo dos pontos das cartas
  (let [cartas-sem-JQK (map JQK->10 cartas)                 ;atualiza as cartas para que sejam no maximo 10
        cartas-com-A11 (map A->11 cartas-sem-JQK)           ;Faz o processo de A nas cartas calculadas
        pontos-com-A1 (reduce + cartas-sem-JQK)             ;Faz calculo com A1
        pontos-com-A11 (reduce + cartas-com-A11)]           ;Faz calculo com A11
    (if (> pontos-com-A11 21) pontos-com-A1 pontos-com-A11))) ;Se pontos com A11 > 21, utiliza a de A1, senão A11

(defn player [nome-jogador]                                 ;Define o novo jogador com parâmetros
  (let [card1 (nova-carta)                                  ;card1 e card2 feitos com fn nova-carta
        card2 (nova-carta)
        cartas [card1 card2]                                ;cartas são card1 e card2
        pontos (pontos-cartas cartas)]                      ;Pontos são a função pontos-cartas sobre as cartas
    {:jogador-nome nome-jogador                             ;map de endereçamento
     :cartas cartas
     :pontos pontos}))

(defn mais-carta [player]                                   ;Função que adiciona uma nova carta
  (let [card (nova-carta)                                   ;utilza função existente
        cards (conj (:cartas player) card)                  ;conj une as cartas existente de player com a nova card
        new-player (update player :cartas conj card)        ;e então o novo-jogador será a atua. das cartas unindo :cartas ao conj carta
        pontos (pontos-cartas cards)]                       ;Assim calculam-se os pontos
    (assoc new-player :pontos pontos)))                     ;e assosiace esses novos pontos ao novo jogador, que é o mesmo com mais carta

(defn player-decision []
  (= (read-line) "sim"))

(defn game [player]
  (println (:player-name player) ", deseja mais uma carta? ")
  (if (player-decision)
    (let [player-com-mais-cartas (mais-carta player)]
      (println player-com-mais-cartas)
      (game player-com-mais-cartas))
    player))

(def player-1 (player "Isaac Borges"))
(println player-1)

(def dealer (player "Dealer"))
(println dealer)


(game player-1)
(game dealer)
;(println (player "Isaac"))
;(println (player "Dealer"))






