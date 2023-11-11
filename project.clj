(defproject zxf3429k "0.0.0"
  :description "zxf3429k"
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
