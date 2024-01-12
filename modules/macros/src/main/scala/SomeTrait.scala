trait SomeTrait[T[_]]

object SomeTrait {
  import language.experimental.macros
  implicit def mkSomeTrait[T[_]]: SomeTrait[T] = macro impl[T]

  import scala.reflect.macros.whitebox.Context
  def impl[T[_]](c: Context)(implicit tt: c.WeakTypeTag[T[?]]) = {
    import c.universe._
    val tp = tt.tpe
    c.info(c.enclosingPosition, s"$tt, $tp, ${showRaw(tp)}", force = true)
    tp match {
      case poly @ PolyType(arg, tr @ TypeRef(_, sym, _)) =>
        if (sym.fullName == "scala.Option")
          c.info(c.enclosingPosition, "Got Option", true)
        else {
          c.info(c.enclosingPosition, s"Wrong type: $tr", true)
          val dealised = tr.dealias
          c.info(c.enclosingPosition, s"Dealised: ${showRaw((arg,dealised))}", true)
          if (dealised.typeSymbol.fullName != "scala.Option")
            c.abort(c.enclosingPosition, "Bad type structure")
        }
      case _ => c.abort(c.enclosingPosition, "Bad type structure")
    }

    q"new SomeTrait[$tp]{}"
  }
}
