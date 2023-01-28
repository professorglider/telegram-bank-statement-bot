package com.tg.bank.bot.routes

import cats.effect.IO
import org.http4s._
import org.http4s.dsl.io._
import doobie._
import doobie.implicits._
import java.time.Instant

object StatementRoutes {

  def statementRoutes(xa: Transactor[IO]): HttpRoutes[IO] = {
    val getHandler: HttpRoutes[IO] = HttpRoutes.of[IO] {
      case GET -> Root / "condition" / name =>
        val query = sql"SELECT name, size, date FROM statements WHERE name = $name"
          .query[(String, Int, Instant)]
          .unique
          .transact(xa)
        for {
          statement <- query
          response <- Ok(statement.asJson)
        } yield response
    }

    val postHandler: HttpRoutes[IO] = HttpRoutes.of[IO] {
      case req @ POST -> Root / "condition" =>
        val file: File = req.headers
          .get(`Content-Disposition`)
          .map(header => File(header.parameters("filename")))
          .getOrElse(throw new Exception("File not found"))
        val size = file.size
        val date = Instant.now()
        val insert =
          sql"INSERT INTO statements (name, size, date) VALUES (${file.name}, $size, $date)"
            .update
            .run
            .transact(xa)
        for {
          _ <- insert
          _ <- file.save
          response <- Ok()
        } yield response
    }

    getHandler orElse postHandler
  }
}
