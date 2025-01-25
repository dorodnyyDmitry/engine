package graphics.shaders

import org.lwjgl.opengl.GL20._
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL33._
import org.lwjgl.opengl.GL33
import org.lwjgl.glfw._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.system.MemoryStack
import graphics.shaders.ShaderID
import tofu.syntax.monadic._
import cats.Monad
import cats.data.EitherT
import tofu.syntax.feither._
import graphics.shaders.error._

// trait Shader

case class Shader(shaderType: ShaderType, shaderCode: ShaderCode) {}
trait ShaderCompiler[F[_]] {
  def compile: F[Either[ShaderError, ShaderID]]
}

case class ShaderCompilerImpl[F[_]: Monad](shader: Shader)
    extends ShaderCompiler[F] {
  private def getShaderSource: Unit =
    GL20.glShaderSource(shader.shaderType.value, shader.shaderCode.code)

  private def compileShader(shaderId: ShaderID): Unit =
    GL20.glCompileShader(shaderId)

  override def compile: F[Either[ShaderError, ShaderID]] = (for {
    shaderId <- EitherT.rightT[F, ShaderError](
      ShaderID(GL20.glCreateShader(shader.shaderType.value))
    )
    _ <- EitherT.rightT[F, ShaderError](getShaderSource)
    _ <- EitherT.rightT[F, ShaderError](compileShader(shaderId))
    _ <- EitherT.cond[F](
      GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == 0,
      (),
      (GenericShaderError(
        s"Failed to create shader $shaderId of type ${shader.shaderType}"
      ): ShaderError)
    )
  } yield shaderId).value
}
