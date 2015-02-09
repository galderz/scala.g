package g.scala.compiler

import scala.tools.nsc.interpreter.IMain
import scala.tools.nsc.{Global, Settings}
import scala.reflect.internal.util.{ScriptSourceFile, BatchSourceFile}
import scala.reflect.io.PlainFile
import scala.tools.nsc.reporters.ConsoleReporter

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object CompileScript210 {

  def main(args : Array[String]) {
    val settings = new Settings()
    settings.usejavacp.value = true
    settings.verbose.value = true
    settings.script.value = scriptMain(settings)
    val interpreter = new IMain(settings)

    // interpreter.interpret("""println("Hello World")""")

    val filePath = "/Users/g/Go/demos/vertx-examples.git/src/raw/scala/http/Server.scala"
    // val filePath = "/Users/g/Go/demos/vertx-examples.git/src/raw/scala/verticle/Server.scala"

    val sourceFile = new BatchSourceFile(PlainFile.fromPath(filePath))
    interpreter.compileSources(ScriptSourceFile(PlainFile.fromPath(filePath), sourceFile.content))



    // interpreter.compileSources(new BatchSourceFile(PlainFile.fromPath(filePath)))

//    val reporter = new ConsoleReporter(settings)
//    val global = Global(settings, reporter)
//    val run = new global.Run
//    run.compile(List(filePath))



    // interpreter.compileString(Source.fromFile(new File(path)).mkString)

//    for (line <- Source.fromFile(new File(path)).getLines())
//      interpreter.interpret(line)

  }

//  def getSourceFile(f: AbstractFile, settings: Settings): BatchSourceFile =
//    if (opt.script.isDefined) ScriptSourceFile(f, reader read f)
//    else new BatchSourceFile(f, reader read f)

  def scriptMain(settings: Settings) = settings.script.value match {
    case "" => "Main"
    case x  => x
  }

}
