import scala.quoted.Quotes
import scala.tasty.inspector as ins
import scala.tasty.inspector.TastyInspector

class Traverser() extends ins.Inspector {

  def inspect(using q: Quotes)(tastys: List[ins.Tasty[q.type]]): Unit = {
    import q.reflect._

    TypeRepr.of[Int] // ok

    TypeRepr.of[String] // fail
    // java.util.NoSuchElementException: None.get while compiling out/app/jar/dest/out.jar
    // Exception in thread "main" java.util.NoSuchElementException: None.get
    //     at scala.None$.get(Option.scala:627)
    //     at scala.None$.get(Option.scala:626)
    //     at dotty.tools.dotc.quoted.QuotesCache$.getTree(QuotesCache.scala:19)
    //     at dotty.tools.dotc.quoted.PickledQuotes$.unpickle(PickledQuotes.scala:183)
    //     at dotty.tools.dotc.quoted.PickledQuotes$.unpickleTypeTree(PickledQuotes.scala:66)
    //     at scala.quoted.runtime.impl.QuotesImpl.unpickleType(QuotesImpl.scala:2975)
    //     at Traverser.inspect(main.scala:11)
    //     at scala.tasty.inspector.TastyInspector$TastyInspectorPhase$1.runOn(TastyInspector.scala:73)
    //     at dotty.tools.dotc.Run.runPhases$4$$anonfun$4(Run.scala:261)
    //     at scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
    //     at scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
    //     at scala.collection.ArrayOps$.foreach$extension(ArrayOps.scala:1323)
    //     at dotty.tools.dotc.Run.runPhases$5(Run.scala:272)
    //     at dotty.tools.dotc.Run.compileUnits$$anonfun$1(Run.scala:280)
    //     at scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18)
    //     at dotty.tools.dotc.util.Stats$.maybeMonitored(Stats.scala:68)
    //     at dotty.tools.dotc.Run.compileUnits(Run.scala:289)
    //     at dotty.tools.dotc.Run.compileUnits(Run.scala:228)
    //     at dotty.tools.dotc.fromtasty.TASTYRun.compile(TASTYRun.scala:12)
    //     at dotty.tools.dotc.Driver.doCompile(Driver.scala:39)
    //     at dotty.tools.dotc.Driver.process(Driver.scala:199)
    //     at dotty.tools.dotc.Driver.process(Driver.scala:167)
    //     at dotty.tools.dotc.Driver.process(Driver.scala:179)
    //     at scala.tasty.inspector.TastyInspector$.inspectFiles(TastyInspector.scala:112)
    //     at scala.tasty.inspector.TastyInspector$.inspectAllTastyFiles(TastyInspector.scala:58)
    //     at main$package$.main(main.scala:32)
    //     at main.main(main.scala:25)
  }

}

@main() def main() = {

  val inspector = Traverser()
  scala.tasty.inspector.TastyInspector.inspectAllTastyFiles(
    Nil,
    // this is the jar of this program. Although we're not actually doing
    // anything, we need to pass in at least one file, otherwise the bug does
    // not manifest itself
    "out/app/jar/dest/out.jar" :: Nil,
    Nil
  )(inspector)

}
