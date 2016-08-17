(ns line.core
  (:require [play-cljs.core :as p]))

(declare game)
(defonce state (atom {}))

(def main-screen
  (reify p/Screen
    (on-show [this]
      (reset! state {:line-y 500}))
    (on-hide [this])
    (on-render [this]
      (p/render game
        [[:fill {:color "black"}
          [:rect {:x 0 :y 0 :width 500 :height 500}]]
         [:fill {}
          [:stroke {:color "white"}
           [:line {:x1 0 :y1 (:line-y @state) :x2 500 :y2 (:line-y @state)}]]]])
      (if-not (< (:line-y @state) 0)
        (swap! state update :line-y dec)
        (swap! state assoc :line-y 500)))
    (on-event [this event])))

(defonce game (p/create-game 500 500))
(doto game
  (p/stop)
  (p/start ["keydown"])
  (p/set-screen main-screen))

