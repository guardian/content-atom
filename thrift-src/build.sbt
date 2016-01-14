name := "content-atom-model-thrift-src"

description := "includable thrift source for the atom model"

unmanagedResourceDirectories in Compile += (baseDirectory in thrift).value / "src/main/thrift"

includeFilter in unmanagedResources := "*.thrift"

autoScalaLibrary := false

crossPaths := false
