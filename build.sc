import mill._
import mill.scalalib._

object engine extends ScalaModule {

  def scalaVersion = "2.13.14"

  object test extends ScalaTests with TestModule.Munit {
    def ivyDeps = Agg(
      ivy"org.scalameta::munit::1.0.1"
    )
  }
}

