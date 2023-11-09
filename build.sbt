import sbtrelease._
import ReleaseStateTransformations._

val contentEntityVersion = "2.2.1"
val scroogeVersion = "22.1.0"   // remember to also update plugins.sbt if the scrooge version changes
val thriftVersion = "0.15.0"    // remember to also update package.json if the thrift version changes

//https://github.com/xerial/sbt-sonatype/issues/103
publishTo := sonatypePublishToBundle.value

lazy val mavenSettings = Seq(
  licenses := Seq("Apache V2" -> url("http://www.apache.org/licenses/LICENSE-2.0.html")),
  publishTo := sonatypePublishToBundle.value,
  publishConfiguration := publishConfiguration.value.withOverwrite(true)
)

val snapshotReleaseType = "snapshot"

lazy val releaseProcessSteps: Seq[ReleaseStep] = {
  val commonSteps:Seq[ReleaseStep] = Seq(
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
  )

  val localExtraSteps:Seq[ReleaseStep] = Seq(
    commitReleaseVersion,
    tagRelease,
    publishArtifacts,
    setNextVersion,
    commitNextVersion
  )

  val snapshotSteps:Seq[ReleaseStep] = Seq(
    publishArtifacts,
    releaseStepCommand("sonatypeReleaseAll")
  )

  val prodSteps:Seq[ReleaseStep] = Seq(
    releaseStepCommandAndRemaining("+publishSigned"),
    releaseStepCommand("sonatypeBundleRelease")
  )

  val localPostRelease:Seq[ReleaseStep]  = Seq(
    pushChanges,
  )

  (sys.props.get("RELEASE_TYPE"), sys.env.get("CI")) match {
    case (Some(v), None) if v == snapshotReleaseType => commonSteps ++ localExtraSteps ++ snapshotSteps ++ localPostRelease
    case (_, None) => commonSteps ++ localExtraSteps ++ prodSteps ++ localPostRelease
    case (Some(v), _) if v == snapshotReleaseType => commonSteps ++ snapshotSteps
    case (_, _)=> commonSteps ++ prodSteps
  }
}

val commonSettings = Seq(
  organization := "com.gu",
  scalaVersion := "2.13.2",
  // downgrade scrooge reserved word clashes to warnings
  Compile / scroogeDisableStrict := true,
  // Scrooge 21.3.0 dropped support for scala < 2.12, so we can only build for Scala 2.12+
  // https://twitter.github.io/scrooge/changelog.html#id11
	crossScalaVersions := Seq("2.12.11", scalaVersion.value),
  scmInfo := Some(ScmInfo(url("https://github.com/guardian/content-atom"),
                          "scm:git:git@github.com:guardian/content-atom.git")),
  releasePublishArtifactsAction := PgpKeys.publishSigned.value,
) ++ mavenSettings

lazy val root = Project(id = "root", base = file("."))
  .settings(commonSettings)
  .aggregate(thrift, scalaClasses)
  .settings(
    publishArtifact := false,
    releaseProcess := releaseProcessSteps
  )

lazy val thrift = Project(id = "content-atom-model-thrift", base = file("thrift"))
  .settings(commonSettings)
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
  .settings(commonSettings)
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
  .settings(commonSettings)
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