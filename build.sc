import mill._
import mill.scalalib._

object engine extends ScalaModule {

  def scalaVersion = "2.13.14"
  
  def detectOs: String = ""

  private val os = "linux"
  private val classifier=s"natives-$os"
  def scalacOptions = Seq("-Ymacro-annotations")
  def ivyDeps = Agg(
      ivy"org.lwjgl:lwjgl:3.3.6",
      ivy"org.lwjgl:lwjgl-opengl:3.3.6",
      ivy"org.lwjgl:lwjgl-openal:3.3.6",
      ivy"org.lwjgl:lwjgl-glfw:3.3.6",
      ivy"org.lwjgl:lwjgl:3.3.6;classifier=$classifier",
      ivy"org.lwjgl:lwjgl-opengl:3.3.6;classifier=$classifier",
      ivy"org.lwjgl:lwjgl-openal:3.3.6;classifier=$classifier",
      ivy"org.lwjgl:lwjgl-glfw:3.3.6;classifier=$classifier",
      ivy"org.typelevel::cats-core:2.12.0",
      ivy"org.typelevel::cats-effect:3.5.7",
      ivy"tf.tofu::tofu-core-ce3:0.13.6",
      ivy"io.estatico::newtype:0.4.4",
      ivy"com.beachape::enumeratum:1.7.5"
  )

  // object test extends ScalaTests with TestModule.Munit {
  //   def ivyDeps = Agg(
  //     ivy"org.scalameta::munit::1.0.1",
  //   )
  // }
}

