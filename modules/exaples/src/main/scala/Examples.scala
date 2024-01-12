object Tst {
  type TT[A] = Option[A]
  implicitly[SomeTrait[TT]] // not seen as `Option` in macro
  implicitly[SomeTrait[({type Q[A] =Option[A]})#Q]] // seen as `Option` in macro
}