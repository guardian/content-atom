import com.github.bigtoast.sbtthrift.ThriftPlugin._
import com.twitter.scrooge.ScroogeSBT

import sbtrelease._

import ReleaseStateTransformations._

Sonatype.sonatypeSettings

organization in ThisBuild := "com.gu"

name := "content-atom-model"

lazy val thrift = (project in file("./thrift")

lazy val scala = (project in file("./scala")).settings(
  ScroogeSBT.newSettings: _*
).settings(
  ScroogeSBT.scroogeThriftSourceFolder in Compile :=
    (baseDirectory in root).value / "src/main/thrift",
  name := "content-atom-model-scala",
  libraryDependencies ++= Seq(
    "org.apache.thrift" % "libthrift" % "0.9.2",
    "com.twitter" %% "scrooge-core" % "3.17.0"
  ),
  crossScalaVersions := Seq("2.10.4", "2.11.7")
)

lazy val root = project in file(".")
  .dependsOn(thrift)
  .dependsOn(scala)

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

// Publish settings

scmInfo := Some(ScmInfo(url("https://github.com/guardian/content-atom"),
                        "scm:git:git@github.com:guardian/contant-atom.git"))

description := "Java library built from Content-atom thrift definition"

pomExtra := (
<url>https://github.com/guardian/content-atom</url>
<developers>
  <developer>
    <id>paulmr</id>
    <name>Paul Roberts</name>
    <url>https://github.com/paulmr</url>
  </developer>
</developers>
)

licenses := Seq("Apache V2" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepTask(PgpKeys.publishSigned),
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeReleaseAll"),
  pushChanges
)
