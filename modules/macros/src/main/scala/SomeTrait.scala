trait SomeTrait[T[_]]

object SomeTrait {
  import language.experimental.macros
  implicit def mkSomeTrait[T[_]]: SomeTrait[T] = macro impl[T]

  import scala.reflect.macros.whitebox.Context
  def impl[T[_]](c: Context)(implicit tt: c.WeakTypeTag[T[?]]) = {
    import c.universe._
    val tp = tt.tpe
    c.info(c.enclosingPosition, s"$tt, $tp, ${showRaw(tp)}", force=true)

    q"new SomeTrait[$tp]{}"
  }
}