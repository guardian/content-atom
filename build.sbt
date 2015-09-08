import com.github.bigtoast.sbtthrift.ThriftPlugin._

libraryDependencies += "org.apache.thrift" % "libthrift" % "0.9.2"

// settings for the thrift plugin, both default and custom
thriftSettings ++ inConfig(Thrift) {

  // add the node option to the js generator, as that is the style of
  // code that we want to generate

  Seq(
    thriftJavaEnabled := false,
    thriftJsEnabled := true,
    thriftJsOptions := Seq("node"),
    thriftOutputDir <<= baseDirectory / "generated",
    thriftJsOutputDir <<= thriftOutputDir
  )
}
