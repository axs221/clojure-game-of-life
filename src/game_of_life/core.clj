(ns game-of-life.core
  (:gen-class))

(defn create-columns []
  (range 10))

(defn create-cell [c]
  (int (rand 2)))

(defn create-row [c]
  (map create-cell (range 10)))

(defn create-board []
  (map create-row (create-columns)))

(defn print-board [board]
  (println (reduce (fn [x y] (concat x "\r\n" y)) board)))

(defn -main
  "Run game of life"
  [& args]
  (let [board (doall (create-board))]
    (print-board board)))
