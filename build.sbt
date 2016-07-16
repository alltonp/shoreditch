import scala.util.Try


name := "shoreditch-api"

organization := "im.mange"

version := Try(sys.env("TRAVIS_BUILD_NUMBER")).map("0.2." + _).getOrElse("1.0-SNAPSHOT")

scalaVersion := "2.11.7"

//crossScalaVersions := Seq("2.10.4", "2.11.0")

libraryDependencies ++= Seq(
  "org.json4s"     %% "json4s-native" % "[3.2.11,3.99.99]" % "provided"
    exclude("org.scala-lang", "scala-compiler")
    exclude("org.scala-lang", "scalap")
    exclude("joda-time", "joda-time"),

  "org.scalatest" % "scalatest_2.11" % "[2.2.1,2.3.0]" % "test"
)

sonatypeSettings

publishTo <<= version { project_version ⇒
  val nexus = "https://oss.sonatype.org/"
  if (project_version.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

homepage := Some(url("https://github.com/alltonp/shoreditch"))

licenses +=("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))

credentials += Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", System.getenv("SONATYPE_USER"), System.getenv("SONATYPE_PASSWORD"))

pomExtra :=
    <scm>
      <url>git@github.com:alltonp/shoreditch.git</url>
      <connection>scm:git:git@github.com:alltonp/shoreditch.git</connection>
    </scm>
    <developers>
      <developer>
        <id>alltonp</id>
      </developer>
    </developers>
