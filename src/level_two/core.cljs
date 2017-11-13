(ns level-two.core
    (:require [clojure.string :as str]))

(enable-console-print!)

(println "This text is printed from src/level-two/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

;; LEVEL-TWO

(let [cast-fate (fn cast-fate [team-size i]
                  (js/confirm
                    (str/join
                      ["cast the mission's fate ("
                       (+ i 1)
                       "/" team-size
                       ") ok=pass cancel=fail" ])))
      game {:agent-count [2 3 3 4 4]};[3 4 4 5 5]
      mission (fn mission [team-size]
                (reduce
                  (fn reveal-fate [j fate]
                    (js/alert (clj->js fate))
                    (+ j 1))
                  1
                  (concat ["drum roll please..."]
                          (shuffle
                            (map
                              #(cast-fate team-size %)
                              (range team-size))))))
      play (fn play [a]
             (doseq [m (:agent-count a)] (mission m)))]
  (aset js/window "run" (fn [] (while (js/confirm "new game?") (play game)))))

((.. js/window -run))
