import com.twitter.scrooge.ScroogeSBT

ScroogeSBT.newSettings

ScroogeSBT.scroogeThriftSourceFolder in Compile :=
  (baseDirectory in thrift).value / "src/main/thrift"

name := "content-atom-model-scala"

description := "scala-compiled version of the content-atom model"

libraryDependencies ++= Seq(
  "org.apache.thrift" % "libthrift" % "0.9.2",
  "com.twitter" %% "scrooge-core" % "3.17.0"
)

crossScalaVersions := Seq("2.10.4", "2.11.7")
