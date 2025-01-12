package graphics.window

import org.lwjgl.glfw.Callbacks._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw._
import org.lwjgl.system.MemoryStack._
import org.lwjgl.system.MemoryUtil._

trait Window {
    val handle: Long
}

object Window {
    def apply(height: Int, width: Int, title: String): Window = new Window{
        override val handle = glfwCreateWindow(height, width, title, NULL, NULL)
    }
}
