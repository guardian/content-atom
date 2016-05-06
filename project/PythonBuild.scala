import sbt._
import sbt.Keys._
import com.github.bigtoast.sbtthrift.ThriftPlugin.{ Thrift, thriftOutputDir }
import scala.io.Source
import java.io.PrintWriter

object PythonBuild {

  val versionPat = "[:VERSION:]"

  lazy val pythonSource = settingKey[File]("base directory for python sources")
  lazy val pythonDist = taskKey[File]("run setup.py")

  lazy val pythonSettings: Seq[Setting[_]] = Seq(
    pythonSource := { baseDirectory.value / "src/main/python" },
    pythonDist <<= (pythonSource, (thriftOutputDir in Thrift), version) map { (src, dest, version) =>
      val pyout = dest / "gen-py"

      val in = Source.fromFile(src / "setup.py").getLines
      val out = new PrintWriter(pyout / "setup.py")

      in.foreach(line => out.println(line.replace(versionPat, version)))
      out.close()

      Process(Seq("python", "setup.py", "check"), Some(pyout)).!
      pyout / "dist" / s"content-atom-${version}.tar.gz"
    }/*,
    managedResources ++= pythonDist */
  )

}
