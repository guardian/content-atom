addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.10.0")
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.7.0")
addSbtPlugin("com.github.sbt" % "sbt-release" % "1.5.0")
addSbtPlugin("com.twitter" % "scrooge-sbt-plugin" % "22.1.0")
addSbtPlugin("com.gu" % "sbt-scrooge-typescript" % "4.0.2")
addSbtPlugin("ch.epfl.scala" % "sbt-version-policy" % "3.2.1")

// Patch the build tool graph without changing published library dependencies.
dependencyOverrides += "org.codehaus.plexus" % "plexus-utils" % "3.6.1"
dependencyOverrides += "org.msgpack" % "msgpack-core" % "0.9.11"
dependencyOverrides += "org.apache.httpcomponents" % "httpclient" % "4.5.13"
