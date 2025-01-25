package graphics.shaders

import org.lwjgl.opengl.GL20
import enumeratum.values.IntEnumEntry
import enumeratum.values.IntEnum
import enumeratum.values.AllowAlias
import enumeratum.{EnumEntry, Enum}

sealed abstract class ShaderType(val value: Int) extends EnumEntry

object ShaderType extends Enum[ShaderType] {
  case object VertexShader   extends ShaderType(value = GL20.GL_VERTEX_SHADER)
  case object FragmentShader extends ShaderType(value = GL20.GL_FRAGMENT_SHADER)

  val values = findValues
}
