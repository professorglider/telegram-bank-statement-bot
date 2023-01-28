package com.tg.bank.bot

import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.implicits.*
import com.tg.bank.bot.routes.StatementRoutes
import org.http4s.dsl.impl.Auth

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    val conditionRoutes = new StatementRoutes().routes
    val authMiddleware = new Auth().authMiddleware
    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(conditionRoutes.orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}