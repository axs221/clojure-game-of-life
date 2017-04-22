(ns game-of-life.core
  (:gen-class))

(defn create-columns []
  (range 5))

(defn create-new-cell []
  (fn [x y] (int (rand 2))))

(defn get-value [board x y]
  (nth (nth board y) x) 1)

(defn count-neighbors [board x y]
  (int(rand 3)))

(defn determine-new-cell [board]
  (fn [x y] 
    (let [previous-value (get-value board x y)
          alive (= previous-value 1)
          neighbors (count-neighbors board x y)]
      (cond 
        (and alive (< neighbors 2)) 0
        (and alive (or (= neighbors 2) (= neighbors 3))) 1
        (and alive (> neighbors 3)) 0
        (and (not alive) (= neighbors 3)) 1
        :else previous-value))))

(defn create-row [create-cell]
  (fn [y] (map (fn [x] (create-cell x y)) (range 5))))

(defn create-board [create-cell]
  (map (create-row create-cell) (create-columns)))

(defn get-cell [c]
  (if (= c 1) "*" " "))

(defn print-row [row]
  ; (reduce (fn [x y] (str x y)) row))
  (reduce (fn [x y] (str x (get-cell y))) "" row))

(defn print-board [board]
  (println (reduce (fn [x y] (concat x "\n" (print-row y))) "" board)))

(defn run-iteration [board]
  (let [new-board (create-board (determine-new-cell board))]
    (print-board new-board)
    new-board))

(defn -main
  "Run game of life"
  [& args]
  (let [board (doall (create-board (create-new-cell)))]
    (loop [new-board board i 1]
      (Thread/sleep 300)
      (if (= i 10)
        1
        (recur (run-iteration board) (inc i))))))
