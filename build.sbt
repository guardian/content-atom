import com.github.bigtoast.sbtthrift.ThriftPlugin._

import sbtrelease._

import ReleaseStateTransformations._

import com.twitter.scrooge.ScroogeSBT

Sonatype.sonatypeSettings

val commonSettings = Seq(
  organization := "com.gu",
  scalaVersion := "2.11.8",
  scmInfo := Some(ScmInfo(url("https://github.com/guardian/content-atom"),
                          "scm:git:git@github.com:guardian/content-atom.git")),

  pomExtra := (
  <url>https://github.com/guardian/content-atom</url>
  <developers>
    <developer>
      <id>paulmr</id>
      <name>Paul Roberts</name>
      <url>https://github.com/paulmr</url>
    </developer>
    <developer>
      <id>LATaylor-guardian</id>
      <name>Luke Taylor</name>
      <url>https://github.com/LATaylor-guardian</url>
    </developer>
    <developer>
      <id>mchv</id>
      <name>Mariot Chauvin</name>
      <url>https://github.com/mchv</url>
    </developer>
    <developer>
      <id>tomrf1</id>
      <name>Tom Forbes</name>
      <url>https://github.com/tomrf1</url>
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

lazy val root = Project(id = "root", base = file("."))
  .aggregate(thrift, scala)
  .settings(commonSettings)
  .settings(
    publishArtifact := false
  )

lazy val thrift = Project(id = "content-atom-model-thrift", base = file("thrift"))
  .settings(commonSettings)
  .disablePlugins(ScroogeSBT)
  .settings(
    resolvers += Resolver.sonatypeRepo("releases"),
    description := "Content atom model Thrift files",
    crossPaths := false,
    publishArtifact in packageDoc := false,
    publishArtifact in packageSrc := false,
    includeFilter in unmanagedResources := "*.thrift",
    unmanagedResourceDirectories in Compile += { baseDirectory.value / "src/main/thrift" },
    libraryDependencies ++= Seq(
    "com.gu" % "content-entity-thrift" % "0.1.0"
    )
  )

lazy val scala = Project(id = "content-atom-model", base = file("scala"))
  .settings(commonSettings)
  .dependsOn(thrift)
  .settings(
    description := "Scala library built from Content-atom thrift definition",
    scroogeThriftSourceFolder in Compile := baseDirectory.value / "../thrift/src/main/thrift",
    includeFilter in unmanagedResources := "*.thrift",
    scroogeThriftDependencies in Compile ++= Seq(
      "content-entity-thrift"
    ),
    // See: https://github.com/twitter/scrooge/issues/199
    scroogeThriftSources in Compile ++= {
      (scroogeUnpackDeps in Compile).value.flatMap { dir => (dir ** "*.thrift").get }
    },
    libraryDependencies ++= Seq(
      "org.apache.thrift" % "libthrift" % "0.9.2",
      "com.twitter" %% "scrooge-core" % "4.5.0"
    )
  )

// settings for the thrift plugin, both default and custom
thriftSettings ++ inConfig(Thrift) {

  // add the node option to the js generator, as that is the style of
  // code that we want to generate

  Seq(
    thriftSourceDir := file("thrift/src/main/thrift"),
    thriftJsEnabled := true,
    thriftJavaEnabled := false,
    thriftJsOptions := Seq("node"),
    thriftOutputDir <<= baseDirectory / "generated",
    thriftJsOutputDir <<= thriftOutputDir
  )
}

