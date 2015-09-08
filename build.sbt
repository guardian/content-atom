import com.github.bigtoast.sbtthrift.ThriftPlugin._

organization := "com.gu"

name := "content-atom-model"

version := "0.2.0-SNAPSHOT"

// this is not a scala application: the JVM compiled version of the
// library is built from auto-generated Java source, so there is no
// need to depend on Scala
autoScalaLibrary := false

crossPaths := false

libraryDependencies += "org.apache.thrift" % "libthrift" % "0.9.2"

// settings for the thrift plugin, both default and custom
thriftSettings ++ inConfig(Thrift) {

  // add the node option to the js generator, as that is the style of
  // code that we want to generate

  Seq(
    thriftJsEnabled := true,
    thriftJsOptions := Seq("node"),
    thriftOutputDir <<= baseDirectory / "generated",
    thriftJsOutputDir <<= thriftOutputDir
  )
}
