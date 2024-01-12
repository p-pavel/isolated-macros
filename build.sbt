lazy val macros = project
  .in(file("modules/macros"))
  .settings(
    scalaVersion := "2.13.12",
    scalacOptions += "-Ymacro-annotations",
    libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.13.12"
  )

lazy val exaples = project
  .dependsOn(macros)
  .in(file("modules/exaples"))
  .settings(
    scalaVersion := "2.13.12",
    scalacOptions ++= Seq(
      "-Ymacro-annotations",
      "-Xprint:typer",
      "-Ymacro-debug-lite"
    )
  )

lazy val all = project.in(file(".")).aggregate(macros, exaples)
