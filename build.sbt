import sbt.Keys.libraryDependencies

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.2"

lazy val root = (project in file("."))
  .settings(name := "workflow-helper")
  .settings(
    libraryDependencies ++= Seq(
      // test dependencies
        "org.scalameta" %% "munit" % "0.7.29" % Test
    )
  )
