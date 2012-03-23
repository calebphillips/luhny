(ns luhny.test.core
  (:use [luhny.core])
  (:use [clojure.test]))

(deftest test-str->ints
  (are [ints string] (= ints (str->ints string))
       [5 6 7 8] "5678"
       [1 2 3 4 5 4 3 2 1] "123454321"))

(deftest test-double-every-second
  (are [x y] (= x (double-every-second y))
       [1 4 3 8 5] [1 2 3 4 5]
       [10 6 14 8] [5 6 7 8]))

(deftest test-ints->digits
  (are [digits ints] (= digits (ints->digits ints))
       [1 3 4 6 5 9 7 1 9 5 2] [13 4 65 9 7 19 52]))

(deftest test-luhny?
  (are [s] (luhny? s)
       "5678"
       "4563960122001999")
  (is (not (luhny? "6789"))))
