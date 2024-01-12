# Illustration of 'implicit macro' problem in Scala 2.13

Here's the isolated example I have with freestyle migration.

Macro in `SomeTrait.scala` shows what type argument is passed to it.

Please note that if type lambda is passed (even without kind projector), we got a correct type (`Option`)
so implicit derivation can proceed if it's depending on the structure of the type.

If just a typealias is passed, only type alias reaches the macro via `WeakTypeTag` so I don't
have access to the real type and it's structure.

If instance derivation depends on type structure, we'll have `abort` inside macro and the implicit value
will be silently not found (in case of `whitebox` macro)
