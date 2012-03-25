(ns luhny.test.core
  (:use [luhny.core])
  (:use [clojure.test]))

(deftest test-str->ints
  (are [ints string] (= ints (str->ints string))
       [5 6 7 8] "5678"
       [1 2 3 4 5 4 3 2 1] "123454321"))

(deftest test-double-every-second
  (are [x y] (= (reverse x) (double-every-second y))
       [1 4 3 8 5] [1 2 3 4 5]
       [10 6 14 8] [5 6 7 8]))

(deftest test-ints->digits
  (are [digits ints] (= digits (ints->digits ints))
       [1 3 4 6 5 9 7 1 9 5 2] [13 4 65 9 7 19 52]))

(deftest test-luhny?
  (are [s] (luhny? s)
       "5678"
       "4563960122001999"
       "378282246310005" ;; Found these in some paypal docs
       "30569309025904"
       "6011000990139424"
       "5555555555554444"
       "4012888888881881"
       "5019717010103742")
  (is (not (luhny? "6789"))))

(deftest test-partition-ignoring
  (is (= [[1 2 :w :x 3]
          [2 :w :x 3 :y :x 4]
          [:w :x 3 :y :x 4 5]
          [:x 3 :y :x 4 5]
          [3 :y :x 4 5]
          [:y :x 4 5 :w 6]
          [:x 4 5 :w 6]
          [4 5 :w 6]
          ]
         (partition-ignoring 3 1 #{:w :x :y}
                             [1 2 :w :x 3 :y :x 4 5 :w 6]))))

(deftest test-cc?
  (is (cc? "4563960122001999"))
  (is (cc? (interleave "4563960122001999"
                       (repeat \-)
                       (repeat \space))))
  (is (not (cc? "ABCABCABCABCABC"))))

(deftest test-mask?
  (is (= "We did some stuff and then went over to the place with that and bought a pizza with XXXXXXXXXXXXXXXX but then I called XXXX XXXX-XXXX XXXX"
         (mask
          "We did some stuff and then went over to the place with that and bought a pizza with 6011000990139424 but then I called 4012 8888-8888 1881")))
  (is (= "Hi XXXXXXXXXXXXXX" (mask "Hi 30569309025904")))
  (is (= "XXXXX XXXXX XXXXX" (mask "37828 22463 10005")))
  )