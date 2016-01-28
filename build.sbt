import com.github.bigtoast.sbtthrift.ThriftPlugin._
import com.twitter.scrooge.ScroogeSBT

import sbtrelease._

import ReleaseStateTransformations._

Sonatype.sonatypeSettings

organization in ThisBuild := "com.gu"

name := "content-atom-model"

scalaVersion := "2.11.7"

// relative to root
lazy val thriftSourceDir = file("src/main/thrift")

lazy val root = (project in file("."))
  .settings(ScroogeSBT.newSettings: _*)
  .settings(
    ScroogeSBT.scroogeThriftSourceFolder in Compile := thriftSourceDir,
    includeFilter in unmanagedResources := "*.thrift",
    unmanagedResourceDirectories in Compile += thriftSourceDir,
    libraryDependencies ++= Seq(
      "org.apache.thrift" % "libthrift" % "0.9.2",
      "com.twitter" %% "scrooge-core" % "3.17.0"
    )
)

crossScalaVersions in ThisBuild := Seq("2.11.7")

releaseCrossBuild := true

// settings for the thrift plugin, both default and custom
thriftSettings ++ inConfig(Thrift) {

  // add the node option to the js generator, as that is the style of
  // code that we want to generate

  Seq(
    thriftJsEnabled := true,
    thriftJavaEnabled := false,
    thriftJsOptions := Seq("node"),
    thriftOutputDir <<= baseDirectory / "generated",
    thriftJsOutputDir <<= thriftOutputDir
  )
}

releasePublishArtifactsAction := PgpKeys.publishSigned.value

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
  publishArtifacts,
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeReleaseAll"),
  pushChanges
)
