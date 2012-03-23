(ns luhny.core)

(defn str->ints [s]
  (map #(Character/digit % 10) s))

(defn double-every-second [s]
  (map #(%1 %2)
       (cycle  [identity #(* % 2)])
       (reverse s)))

(defn ints->digits [s]
  (mapcat #(str->ints (str %)) s))

(def add (partial reduce +))

(defn luhny? [s]
  (-> s
      str->ints
      double-every-second
      ints->digits
      add
      (mod 10)
      zero?))


(defn partition-ignoring
  "Acts like the 3 argument version of partition, except that it 'ignores'
   the characters in the ignore set.  It includes them in the partiion,
   but does not count them when building the partition.
   The idea is to use this to divide a sequences of characters up into partitions
   of n characters, not counting spaces and hyphens, but preserving them so that
   we can spit them back out once we mask any cc numbers"
  [n step ignore coll]
  (let [cnt-ignoring #(count (remove ignore %))
        full-partition? #(= n (cnt-ignoring %))
        take-ignoring #(loop [c % taken []]
                         (if (or (full-partition? taken) (empty? c))
                           taken
                           (recur (rest c) (conj taken (first c)))))]
    (lazy-seq
     (when-let [s (seq coll)]
       (let [p (take-ignoring s)]
         (when (full-partition? p)
           (cons p (partition-ignoring n step ignore (nthnext s step)))))))))
