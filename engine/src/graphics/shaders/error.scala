package graphics.shaders

object error {
  trait ShaderError{
    val errorCode: String
    val errorMessage: String
  }

  case class GenericShaderError(message: String) extends ShaderError {

    override val errorCode: String = "SHADER_ERROR"

    override val errorMessage: String = message

  }
}
