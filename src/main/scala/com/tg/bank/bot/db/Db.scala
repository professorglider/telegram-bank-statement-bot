package com.tg.bank.bot.db

import cats.effect.{IO, Resource}
import doobie._
import doobie.hikari._

object Db {

  val xa: Resource[IO, HikariTransactor[IO]] = for {
    ce <- ExecutionContexts.fixedThreadPool[IO](32) // our connect EC
    te <- ExecutionContexts.cachedThreadPool[IO]    // our transaction EC
    xa <- HikariTransactor.newHikariTransactor[IO](
      "org.postgresql.Driver",                            // driver classname
      "jdbc:postgresql://localhost:5432/mydatabase",      // connect URL
      "myuser",                                         // username
      "mypassword",                                      // password
      ce                                               // connect EC
    )
//    _ <- Resource.liftF(Migrations.run(xa))
  } yield xa

}
