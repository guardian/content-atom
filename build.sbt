import sbtrelease.*
import ReleaseStateTransformations.*
import sbt.Keys.scmInfo
import sbtversionpolicy.withsbtrelease.ReleaseVersion

import java.net.URI

val contentEntityVersion = "4.0.0"
val scroogeVersion = "22.1.0"   // remember to also update plugins.sbt if the scrooge version changes
val thriftVersion = "0.20.0"    // remember to also update package.json if the thrift version changes

val artifactProductionSettings = Seq(
  organization := "com.gu",
  scalaVersion := "2.13.14", // bug prevents building json package at >= 2.13.15
  // downgrade scrooge reserved word clashes to warnings
  Compile / scroogeDisableStrict := true,
  // Scrooge 21.3.0 dropped support for scala < 2.12, so we can only build for Scala 2.12+
  // https://twitter.github.io/scrooge/changelog.html#id11
  crossScalaVersions := Seq("2.12.21", scalaVersion.value),
  licenses := Seq(License.Apache2)
  /*
  Test / testOptions +=
    Tests.Argument(TestFrameworks.ScalaTest, "-u", s"test-results/scala-${scalaVersion.value}", "-o")
   */ // Need to uncomment when testcases gets added.Also to change ci.yml to add "Build and Test" as well.
)

lazy val root = Project(id = "root", base = file("."))
  .aggregate(thrift, scalaClasses, json)
  .settings(
    publish / skip := true,
    releaseVersion := ReleaseVersion.fromAggregatedAssessedCompatibilityWithLatestRelease().value,
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
    Compile / unmanagedResourceDirectories += { baseDirectory.value / "src/main/thrift" },
  )

lazy val scalaClasses = Project(id = "content-atom-model", base = file("scala"))
  .settings(artifactProductionSettings)
  .settings(
    description := "Scala library built from Content-atom thrift definition",
    Compile / scroogeThriftSourceFolder := baseDirectory.value / "../thrift/src/main/thrift",
    Compile / scroogeThriftOutputFolder := sourceManaged.value,
    Compile / managedSourceDirectories += (Compile / scroogeThriftOutputFolder).value,
    Compile / scroogeThriftDependencies ++= Seq("content-entity-thrift"),
    libraryDependencies ++= Seq(
      "com.gu" % "content-entity-thrift" % contentEntityVersion,
      "com.gu" %% "content-entity-model" % contentEntityVersion,
      "org.apache.thrift" % "libthrift" % thriftVersion,
      "com.twitter" %% "scrooge-core" % scroogeVersion
    ),
    // Include the Thrift file in the published jar
    Compile / scroogePublishThrift := true
  )

lazy val json = Project(id = "content-atom-model-json", base = file("json"))
  .settings(artifactProductionSettings)
  .dependsOn(scalaClasses)
  .disablePlugins(ScroogeSBT)
  .settings(
    description := "Circe json encoders and decoders for content atom model",
    libraryDependencies ++= Seq(
      "com.gu" %% "content-entity-model-json" % "4.1.0-PREVIEW.ancirce-encoder-decoders.2026-06-29T1027.e9a20d9a",
      "com.gu" %% "fezziwig" % "2.0.1",
      "io.circe" %% "circe-parser" % "0.14.15",
    )
  )

lazy val npmPreviewReleaseTagMaybe = if (sys.env.get("RELEASE_TYPE").contains("PREVIEW_FEATURE_BRANCH")) {
  Seq(scroogeTypescriptPublishTag := "preview")
} else Seq.empty

lazy val typescriptClasses = (project in file("ts"))
  .enablePlugins(ScroogeTypescriptGen)
  .settings(artifactProductionSettings)
  .settings(npmPreviewReleaseTagMaybe)
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
    ),
    // for npm publish via sbt, scmInfo is used instead of `repository` in package.json
    scmInfo := Some(ScmInfo(URI.create("https://github.com/guardian/content-atom").toURL,
      "scm:git:https://github.com/guardian/content-atom.git"))
  )
