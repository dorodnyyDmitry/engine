package graphics

import io.estatico.newtype.macros.newsubtype

package object shaders {
  @newsubtype
  case class ShaderCode(code: String)

  @newsubtype 
  case class ShaderID(id: Int)
}
