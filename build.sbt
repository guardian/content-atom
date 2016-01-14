import sbtrelease._

import ReleaseStateTransformations._

Sonatype.sonatypeSettings

organization in ThisBuild := "com.gu"

lazy val thrift = project

lazy val scala = project

// include the thrift source in the jar file so that it can be used as a
// dependency for applications that wish to use this model to generate thier
// own thrift rather than directly via the compiled code.

lazy val `thrift-src` = project

// Publish settings

scmInfo in ThisBuild := Some(ScmInfo(url("https://github.com/guardian/content-atom"),
                        "scm:git:git@github.com:guardian/contant-atom.git"))

pomExtra in ThisBuild := (
<url>https://github.com/guardian/content-atom</url>
<developers>
  <developer>
    <id>paulmr</id>
    <name>Paul Roberts</name>
    <url>https://github.com/paulmr</url>
  </developer>
</developers>
)

licenses in ThisBuild := Seq("Apache V2" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))

releaseProcess in ThisBuild := Seq[ReleaseStep](
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
