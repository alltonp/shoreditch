import scala.util.Try
import bintray.Keys._

//sonatypeSettings

name := "shoreditch"

organization := "im.mange"

version := Try(sys.env("LIB_VERSION")).getOrElse("1")

scalaVersion := "2.10.4"

crossScalaVersions := Seq("2.10.4"/*, "2.11.0"*/)

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.10" % "2.1.4" % "test",
  "net.liftweb" %% "lift-webkit" % "2.5.1"
)

bintrayPublishSettings

repository in bintray := "repo"

bintrayOrganization in bintray := None

publishMavenStyle := true

publishArtifact in Test := false

homepage := Some(url("https://github.com/alltonp/shoreditch"))

licenses +=("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))

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
