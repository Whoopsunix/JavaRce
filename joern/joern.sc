import java.io.*

import io.shiftleft.codepropertygraph.generated.nodes.Call
import io.shiftleft.codepropertygraph.generated.nodes.Method

@main def run(cpgFile: String, outFile: String) = {
  importCpg(cpgFile)

//  def results = exec() ::: jdbc()
  def results = test()

  results.foreach { line =>
    println(line)
    fileWrite(outFile, line)
  }

  // 写入文件
  //  results #> outFile
}

// test
def exec(): List[String] = {
  def source = getSource()

  def param = source.parameter

  def sink = cpg.call.methodFullName("java.lang.Runtime.*").typeFullName("java.lang.Process")

  def results = sink.reachableByFlows(param).p

  return results
}

// JDBC
def jdbc(): List[String] = {
  def source = getSource()

  def param = source.parameter

  def sink = cpg.call.methodFullName("java.sql.DriverManager.getConnection.*").typeFullName("java.sql.Connection")

  def results = sink.reachableByFlows(param).p

  return results
}

// 命令执行
def exec(): List[String] = {
  def source = getSource()

  def param = source.parameter

  def sink1 = cpg.call.methodFullName("java.lang.Runtime.*").typeFullName("java.lang.Process")

  def sink2 = cpg.call.methodFullName("java.lang.ProcessBuilder.start.*").typeFullName("java.lang.Process")

  def results = sink1.reachableByFlows(param).p ++ sink2.reachableByFlows(param).p

  return results
}

// todo @main 中获取 source 再传入会查不到，很怪
def getSource(): Iterator[Method] = {
  // @*Mapping 注解
  def source = cpg.method.where(_.annotation.name(".*Mapping"))

  return source
}

def fileWrite(fileName: String, textData: String): Unit = {
  val fileWriter = new FileWriter(fileName, true)
  val bufferedWriter = new BufferedWriter(fileWriter)
  val printWriter = new PrintWriter(bufferedWriter)
  try {
    printWriter.println(textData)
  } finally {
    printWriter.close()
  }
}
