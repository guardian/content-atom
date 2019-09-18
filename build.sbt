import sbtrelease._
import ReleaseStateTransformations._
import Classpaths.managedJars

lazy val jarsToExtract = TaskKey[Seq[File]]("jars-to-extract", "JAR files to be extracted")

lazy val extractJarsTarget = SettingKey[File]("extract-jars-target", "Target directory for extracted JAR files")

lazy val extractJars = TaskKey[Unit]("extract-jars", "Extracts JAR files")

lazy val extractJarSettings = Defaults.coreDefaultSettings ++ Seq(
  // collect jar files to be extracted from managed jar dependencies
  jarsToExtract := { managedJars(Compile, classpathTypes.value, update.value) map { _.data } filter { _.getName.startsWith("content-entity-thrift") } },

  // define the target directory
  extractJarsTarget := { baseDirectory(_ / "thrift/target/extracted").value },

  // task to extract jar files
  extractJars := { jarsToExtract.value foreach { jar =>
      // streams.value.log.info("Extracting " + jar.getName + " to " + extractJarsTarget.value)
      IO.unzip(jar, extractJarsTarget.value)
    }
  }
)

val commonSettings = Seq(
  organization := "com.gu",
  scalaVersion := "2.13.0",
	crossScalaVersions := Seq("2.11.12", "2.12.10", scalaVersion.value),
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
    <developer>
      <id>annebyrne</id>
      <name>Anne Byrne</name>
      <url>https://github.com/annebyrne</url>
    </developer>
    <developer>
      <id>regiskuckaertz</id>
      <name>Regis Kuckaertz</name>
      <url>https://github.com/regiskuckaertz</url>
    </developer>
  </developers>
  ),
  licenses := Seq("Apache V2" -> url("http://www.apache.org/licenses/LICENSE-2.0.html")),

  publishTo := sonatypePublishTo.value,
  publishConfiguration := publishConfiguration.value.withOverwrite(true),
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
    releaseStepCommand("sonatypeRelease"),
    pushChanges
  )
)

lazy val root = Project(id = "root", base = file("."))
  .aggregate(thrift, scalaClasses)
  .settings(commonSettings)
  .settings(extractJarSettings: _*)
  .settings(publishArtifact := false)

lazy val thrift = Project(id = "content-atom-model-thrift", base = file("thrift"))
  .settings(commonSettings)
  .disablePlugins(ScroogeSBT)
  .settings(
    description := "Content atom model Thrift files",
    crossPaths := false,
    publishArtifact in packageDoc := false,
    publishArtifact in packageSrc := false,
    includeFilter in unmanagedResources := "*.thrift",
    unmanagedResourceDirectories in Compile += { baseDirectory.value / "src/main/thrift" }
  )

lazy val scalaClasses = Project(id = "content-atom-model", base = file("scala"))
  .settings(commonSettings)
  .settings(
    description := "Scala library built from Content-atom thrift definition",
    scroogeThriftSourceFolder in Compile := baseDirectory.value / "../thrift/src/main/thrift",
    scroogeThriftOutputFolder in Compile := sourceManaged.value,
    scroogeThriftDependencies in Compile ++= Seq("content-entity-thrift"),
    resolvers += Resolver.sonatypeRepo("public"),
    libraryDependencies ++= Seq(
      "com.gu" % "content-entity-thrift" % "2.0.2",
      "com.gu" %% "content-entity-model" % "2.0.2",
      "org.apache.thrift" % "libthrift" % "0.12.0",
      "com.twitter" %% "scrooge-core" % "19.9.0"
    ),
    // Include the Thrift file in the published jar
    scroogePublishThrift in Compile := true
  )
