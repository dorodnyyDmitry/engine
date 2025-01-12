package graphics.window

import tofu.syntax.monadic._
import cats.Monad
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.glfw._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL
import cats.effect.Deferred
import cats.effect.kernel.GenConcurrent
import cats.effect.Concurrent
import tofu.syntax.race.RaceOps
import tofu.syntax.start
import cats.effect.kernel.Clock
import cats.effect.kernel.Temporal
import scala.concurrent.duration.Duration
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl._
import java.util.concurrent.TimeUnit
import tofu.syntax.feither._
import tofu.syntax.either._
import cats.syntax.either._
// import cats.syntax.either._
// import cats.effect.{Async, Fiber, CancelToken}

trait Amogus[F[_]] {}

class AmogusImpl[F[_]: Monad: Concurrent: Temporal] extends Amogus[F] {

  private def keyCallback(): GLFWKeyCallback =
    new GLFWKeyCallback {
      override def invoke(
          window: Long,
          key: Int,
          scancode: Int,
          action: Int,
          mods: Int
      ): Unit = {
        println(
          s"key press detected: key $key, action $action, window $window, scancode $scancode"
        )
        if (key == GLFW_KEY_ESCAPE) {
          println("close window")
          glfwSetWindowShouldClose(window, true)
        }
      }
    }

  def initWindow() = for {
    _              <- println("xd").pure[F]
    errorCallback  <- GLFWErrorCallback.createPrint(System.err).set.pure[F]
    glfwInitStatus <- glfwInit().pure[F]
    _              <- glfwDefaultWindowHints().pure[F]
    window         <- Window(300, 300, "Test").pure[F]
    _              <- println("window created").pure[F]
    _              <- glfwMakeContextCurrent(window.handle).pure[F]
    _              <- glfwSetKeyCallback(window.handle, keyCallback).pure[F]
    _              <- glfwShowWindow(window.handle).pure[F]
  } yield window

  private def checkClose(a: F[Window]): F[Either[F[Window], Unit]] = {
    a.flatMap(w =>
      glfwWindowShouldClose(w.handle) match {
        case true  => ().asRightF
        case false => a.asLeftF
      }
    )

  }

  final def loop(window: Window): F[Unit] =
    (
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT).pure[F]
        >> glfwSwapBuffers(window.handle).pure[F]
        >> glfwPollEvents().pure[F]
        >> Temporal[F].sleep(Duration(1000L / 60L, TimeUnit.MILLISECONDS))
        >> window.pure[F]
    ).tailRecM(checkClose)

  def balls = for {
    window <- initWindow
    _      <- glfwShowWindow(window.handle).pure[F]
    _      <- GL.createCapabilities.pure[F]
    _      <- glClearColor(1.0f, 1.0f, 0.0f, 0.0f).pure[F]
    _      <- loop(window)
    _      <- glfwDestroyWindow(window.handle).pure[F]
  } yield ()
}

object Amogus {
  def apply[F[_]: Monad: Concurrent: Temporal]: Amogus[F] = new AmogusImpl[F]
}
