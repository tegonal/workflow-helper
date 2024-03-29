import sbt.Keys.libraryDependencies

ThisBuild / version := "v0.4.0-SNAPSHOT"
ThisBuild / organization := "Tegonal GmbH"

ThisBuild / scalaVersion := "3.4.1"

lazy val root = (project in file("."))
  .settings(name := "workflow-helper")
  .settings(
    libraryDependencies ++= Seq(
      // test dependencies
      "org.scalameta" %% "munit" % "0.7.29" % Test
    )
  )
