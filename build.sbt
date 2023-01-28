name := "telegram-bank-statement-bot"

version := "0.1"

scalaVersion := "3.2.1"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.9.0",
  "dev.zio" %% "zio" % "2.0.6",
  "dev.zio" %% "zio-interop-cats" % "23.0.0.1",
  "org.tpolecat" %% "doobie-core" % "1.0.0-RC2",
  "org.tpolecat" %% "doobie-postgres" % "1.0.0-RC2",
  "org.tpolecat" %% "doobie-hikari" % "1.0.0-RC2",
  "org.http4s" %% "http4s-core" % "1.0.0-M38",
  "org.http4s" %% "http4s-dsl" % "1.0.0-M38",
  "org.http4s" %% "http4s-blaze-server" % "1.0.0-M38",
  "org.http4s" %% "http4s-circe" % "1.0.0-M38",
  "io.circe" %% "circe-generic" % "0.14.3",
  "io.circe" %% "circe-parser" % "0.14.3",
  "com.github.jwt-scala" %% "jwt-core" % "9.1.2",
  "com.github.jwt-scala" %% "jwt-circe" % "9.1.2"
)
