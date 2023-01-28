package com.tg.bank.bot.auth

import java.time.Instant
import java.util.UUID
import cats.data.EitherT
import cats.effect.IO
import io.circe.Json
import io.circe.parser.decode
import pdi.jwt.*

import scala.util.Success

object Auth {
  val secretKey = "mysecretkey"
  val algorithm = JwtAlgorithm.HS256

  def createToken(userId: String): IO[String] = IO {
    val header: JwtHeader = JwtHeader(algorithm)
    val claim = JwtClaim() + ("sub", userId) + ("exp", Instant.now.plusSeconds(3600))
    JwtCirce.encode(claim, secretKey, algorithm)
  }

  def decodeToken(token: String): IO[Option[JwtClaim]] = IO {
    JwtCirce.decode(token, secretKey, Seq(algorithm)) match {
      case Success(jwt) => Some(jwt)
      case _ => None
    }
  }

  def authenticate(token: String): EitherT[IO, String, JwtClaim] = {
    EitherT(decodeToken(token).map {
      case None => Left("Invalid token")
      case Some(claim) => Right(claim)
    })
  }
}
