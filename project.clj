(defproject ertu/odin "0.1.0"
  :description "A declarative query DSL for Clojure - halgari/odin"
  :url "https://github.com/ertugrulcetin/odin"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.9.293"]
                 [org.clojure/test.check "0.9.0"]]
  ;:jvm-opts ["-agentpath:/Users/tim/lib/libyjpagent.jnilib"]
  :profiles {:dev {:dependencies [[org.clojure/data.xml "0.0.8"]
                                  [com.datomic/datomic-free "0.9.5544"]]}}
  :plugins [[autodoc/lein-autodoc "1.1.1"]
            [lein-doo "0.1.7"]
            [lein-cljsbuild "1.1.4"]]

  :cljsbuild
  {:builds [{:id           "test"
             :source-paths ["src" "test"]
             :compiler     {:output-to     "resources/public/js/testable.js"
                            :optimizations :simple}}]})
