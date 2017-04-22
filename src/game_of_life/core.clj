(ns game-of-life.core
  (:gen-class))

(def board-width 20)
(def board-height 20)

(defn create-columns []
  (range board-width))

(defn create-random-cell []
  (fn [x y] (int (rand 2))))

(defn get-value [board x y]
  (cond
    (>= x (count board)) 0
    (< x 0) 0
    (>= y (count (nth board 0))) 0
    (< y 0) 0
    :else (nth (nth board y) x)))

(defn count-neighbors [board x y]
  (reduce (fn [prev xy] (+ prev (get-value board (xy 0) (xy 1)))) 0 [
                     [(- x 1) (- y 1)]
                     [(- x 1) y]
                     [(- x 1) (+ y 1)]
                     [x (- y 1)]
                     [x (+ y 1)]
                     [(+ x 1) (- y 1)]
                     [(+ x 1) y]
                     [(+ x 1) (+ y 1)]
                     ]))

(defn determine-if-cell-is-alive [board]
  (fn [x y] 
    ; (println (get-value board x y))
    ; (println (count-neighbors board x y))
    (let [previous-value (get-value board x y)
          alive (= previous-value 1)
          neighbors (count-neighbors board x y)]
      ; (println x y previous-value alive neighbors)
      (cond 
        (and alive (< neighbors 2)) 0
        (and alive (or (= neighbors 2) (= neighbors 3))) 1
        (and alive (> neighbors 3)) 0
        (and (not alive) (= neighbors 3)) 1
        :else previous-value))))

(defn create-row [create-cell]
  (fn [y] (map (fn [x] (create-cell x y)) (range board-height))))

(defn create-board [create-cell]
  (map (create-row create-cell) (create-columns)))

(defn get-cell [c]
  (if (= c 1) "X" "."))

(defn print-row [row]
  (reduce (fn [x y] (str x (get-cell y))) "" row))

(defn print-board [board]
  (println (reduce (fn [x y] (concat x "\n" (print-row y))) "" board)))

(defn run-iteration [board]
  (let [new-board (create-board (determine-if-cell-is-alive board))]
    (print-board new-board)
    new-board))

(defn -main
  "Run game of life"
  [& args]
  (let [board (doall (create-board (create-random-cell)))]
    ; (println (get-value board 1 1))))
    (loop [new-board board i 1]
      (Thread/sleep 120)
      (if (= i 100)
        1
        (recur (run-iteration new-board) (inc i))))))
