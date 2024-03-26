import sbtrelease._
import ReleaseStateTransformations._

val contentEntityVersion = "3.0.3"
val scroogeVersion = "22.1.0"   // remember to also update plugins.sbt if the scrooge version changes
val thriftVersion = "0.15.0"    // remember to also update package.json if the thrift version changes

val artifactProductionSettings = Seq(
  organization := "com.gu",
  scalaVersion := "2.13.12",
  // downgrade scrooge reserved word clashes to warnings
  Compile / scroogeDisableStrict := true,
  // Scrooge 21.3.0 dropped support for scala < 2.12, so we can only build for Scala 2.12+
  // https://twitter.github.io/scrooge/changelog.html#id11
	crossScalaVersions := Seq("2.12.18", scalaVersion.value),
  licenses := Seq(License.Apache2)
  /*
  Test / testOptions +=
    Tests.Argument(TestFrameworks.ScalaTest, "-u", s"test-results/scala-${scalaVersion.value}", "-o")
   */ // Need to uncomment when testcases gets added.Also to change ci.yml to add "Build and Test" as well.
)

lazy val root = Project(id = "root", base = file("."))
  .aggregate(thrift, scalaClasses)
  .settings(
    publish / skip := true,
    releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runClean,
      runTest,
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      setNextVersion,
      commitNextVersion
    )
  )

lazy val thrift = Project(id = "content-atom-model-thrift", base = file("thrift"))
  .settings(artifactProductionSettings)
  .disablePlugins(ScroogeSBT)
  .settings(
    description := "Content atom model Thrift files",
    crossPaths := false,
    packageDoc / publishArtifact := false,
    packageSrc / publishArtifact := false,
    unmanagedResources / includeFilter := "*.thrift",
    Compile / unmanagedResourceDirectories += { baseDirectory.value / "src/main/thrift" }
  )

lazy val scalaClasses = Project(id = "content-atom-model", base = file("scala"))
  .settings(artifactProductionSettings)
  .settings(
    description := "Scala library built from Content-atom thrift definition",
    Compile / scroogeThriftSourceFolder := baseDirectory.value / "../thrift/src/main/thrift",
    Compile / scroogeThriftOutputFolder := sourceManaged.value,
    Compile / scroogeThriftDependencies ++= Seq("content-entity-thrift"),
    resolvers += Resolver.sonatypeRepo("public"),
    libraryDependencies ++= Seq(
      "com.gu" % "content-entity-thrift" % contentEntityVersion,
      "com.gu" %% "content-entity-model" % contentEntityVersion,
      "org.apache.thrift" % "libthrift" % thriftVersion,
      "com.twitter" %% "scrooge-core" % scroogeVersion
    ),
    // Include the Thrift file in the published jar
    Compile / scroogePublishThrift := true
  )


lazy val typescriptClasses = (project in file("ts"))
  .enablePlugins(ScroogeTypescriptGen)
  .settings(artifactProductionSettings)
  .settings(
    name := "content-atom-typescript",
    scroogeTypescriptNpmPackageName := "@guardian/content-atom-model",
    Compile / scroogeDefaultJavaNamespace := scroogeTypescriptNpmPackageName.value,
    Test / scroogeDefaultJavaNamespace := scroogeTypescriptNpmPackageName.value,
    description := "Typescript library built from Content-atom thrift definition",

    Compile / scroogeLanguages := Seq("typescript"),
    Compile / scroogeThriftSourceFolder := baseDirectory.value / "../thrift/src/main/thrift",

    scroogeTypescriptPackageLicense := "Apache-2.0",
    Compile / scroogeThriftDependencies ++= Seq("content-entity-thrift"),
    scroogeTypescriptPackageMapping := Map(
      "content-entity-thrift" -> "@guardian/content-entity-model"
    ),
    libraryDependencies ++= Seq(
      "com.gu" % "content-entity-thrift" % contentEntityVersion
    )
  )