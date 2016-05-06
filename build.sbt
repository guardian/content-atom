import com.github.bigtoast.sbtthrift.ThriftPlugin._
import com.twitter.scrooge.ScroogeSBT

import sbtrelease._

import ReleaseStateTransformations._

Sonatype.sonatypeSettings

val commonSettings = Seq(
  organization := "com.gu",
  scalaVersion := "2.11.8",
  scmInfo := Some(ScmInfo(url("https://github.com/guardian/content-atom"),
                          "scm:git:git@github.com:guardian/contant-atom.git")),

  pomExtra := (
  <url>https://github.com/guardian/content-atom</url>
  <developers>
    <developer>
      <id>paulmr</id>
      <name>Paul Roberts</name>
      <url>https://github.com/paulmr</url>
    </developer>
  </developers>
  ),
  licenses := Seq("Apache V2" -> url("http://www.apache.org/licenses/LICENSE-2.0.html")),

  releasePublishArtifactsAction := PgpKeys.publishSigned.value,
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    publishArtifacts,
    setNextVersion,
    commitNextVersion,
    releaseStepCommand("sonatypeReleaseAll"),
    pushChanges
  )
)

lazy val root = (project in file("."))
  .aggregate(thrift, scala)
  .settings(commonSettings)
  .settings(
    publishArtifact := false
  )

lazy val scala = (project in file("scala"))
  .settings(ScroogeSBT.newSettings: _*)
  .settings(commonSettings)
  .settings(
    name := "content-atom-model",
    description := "Scala library built from Content-atom thrift definition",

    scroogeThriftSourceFolder in Compile := baseDirectory.value / "../thrift/src/main/thrift",
    includeFilter in unmanagedResources := "*.thrift",
    unmanagedResourceDirectories in Compile += baseDirectory.value / "../thrift/src/main/thrift",
    managedSourceDirectories in Compile += (scroogeThriftOutputFolder in Compile).value,
    libraryDependencies ++= Seq(
      "org.apache.thrift" % "libthrift" % "0.9.2",
      "com.twitter" %% "scrooge-core" % "3.17.0"
    )
  )

lazy val thrift = (project in file("thrift"))
  .settings(commonSettings)
  .settings(
    name := "content-atom-model-thrift",
    description := "Content atom model Thrift files",
    crossPaths := false,
    publishArtifact in packageDoc := false,
    publishArtifact in packageSrc := false,
    unmanagedResourceDirectories in Compile += { baseDirectory.value / "src/main/thrift" }
  )

// settings for the thrift plugin, both default and custom
thriftSettings ++ PythonBuild.pythonSettings ++ inConfig(Thrift) {

  // add the node option to the js generator, as that is the style of
  // code that we want to generate

  Seq(
    thriftSourceDir     := file("thrift/src/main/thrift"),
    thriftJsEnabled     := true,
    thriftJavaEnabled   := false,
    thriftPythonEnabled := true,
    thriftJsOptions     := Seq("node"),
    thriftOutputDir     <<= baseDirectory / "generated",
    thriftJsOutputDir   <<= thriftOutputDir
  )
}
