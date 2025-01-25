package graphics.window

import cats.effect.IO
import cats.effect.IO._
import cats.effect.IOApp
import org.lwjgl.glfw._
import org.lwjgl.system.MemoryStack._
import org.lwjgl.system.MemoryUtil._
import org.lwjgl.glfw.GLFW._
import cats.effect.ExitCode
import org.lwjgl.opengl.GL

object Application extends IOApp {

  val amogus = new AmogusImpl[IO]
  override def run(args: List[String]): IO[ExitCode] = {
    amogus.balls.evalOn(MainThread) >>
      IO.pure(
        ExitCode.Success
      )
  }

}
