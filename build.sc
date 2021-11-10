// install mill:
// curl -sS -L "https://github.com/com-lihaoyi/mill/releases/download/0.9.9/0.9.9" > mill && chmod +x mill

import mill._, scalalib._

object app extends ScalaModule {
  def scalaVersion = "3.1.0"
  def ivyDeps = Agg(
    ivy"org.scala-lang::scala3-tasty-inspector:${scalaVersion()}"
  )
}
