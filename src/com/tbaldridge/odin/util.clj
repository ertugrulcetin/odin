(ns com.tbaldridge.odin.util
  (:require [clojure.walk :as walk]
            [clojure.string :as str]))



(defmacro efor [[bind coll & rest] body]
  (if rest
    (cond
      (= :let bind)
      `(let [~@coll]
         (efor ~rest ~body))

      (= :when bind)
      `(when ~coll
         (efor ~rest ~body))

      :else
      `(eduction
         (mapcat (fn [~bind]
                   (efor ~rest ~body)))
         ~coll))

    (cond
      (= :let bind)
      `(let [~@coll]
         (cons ~body nil))

      (= :when bind)
      `(when ~coll
         (cons ~body nil))

      :else
      `(eduction
         (map
           (fn [~bind]
             ~body))
         ~coll))))

(defn query-var? [v]
  (and (symbol? v)
       (not (namespace v))
       (str/starts-with? (name v) "?")))


(defn body-lvars [form]
  (let [lvars (atom #{})
        form (walk/postwalk
               (fn [v]
                 (cond
                   (query-var? v)
                   (do (swap! lvars conj v)
                       v)

                   (= v '_)
                   `(com.tbaldridge.odin.unification/lvar)

                   :else v))
               form)]


    [@lvars form]))


(defn transform [data pth f & args]
  (let [inner (fn transform-inner [data [h & t :as path] f args]
                (println data h)
                (if path
                  (if-let [sub-data (get data h)]
                    (let [result (transform-inner sub-data t f args)]
                      (if (identical? sub-data result)
                        data
                        (assoc data h result)))
                    data)
                  (apply f data args)))]
    (inner data pth f args)))