package com.micinfotech.genome

import java.io.File

import scala.collection.immutable.ListMap
import scala.io.Source

/**
  * Created by sovar on 11/21/16.
  */
case class VcfRow(chrom: Char, pos: Int, id: String, ref: String, alt: String, qual: Int, filter: String, info: String, format: String, alleles: Seq[String])
class Vcf {
  val header = ListMap.empty[String, Either[String, ListMap[String, String]]]
  val samples = Map.empty[String, Int]
  var vcfrows: Iterator[VcfRow] = _
}
object Vcf {
  /*def read(file: File): Iterator[VcfRow] = {
    val multi = """^(.*)=<(.*)>$""".r
    val single = """^(.*)=(.*)$""".r
    var header = ListMap.empty[String, Either[String, ListMap[String, String]]]
    val line = Source.fromFile(file).getLines().buffered
    line.head match {
      case x if multi.findFirstMatchIn(x).isDefined => 0
      case single => 1
    }
    while (line.head.startsWith("##")) {
      val head = line.head
      var x = multi.findFirstMatchIn(head)
//      if (x.isDefined)
//        header += (x.get.group(1), Right(x.get.group(2)))
//      else {
//        x = single.findFirstMatchIn(head)
//        if (x.isDefined) header += (x.get.group(1), Left(x.get.group(2)))
//      }
      line.next()
    }
    line.map(x => VcfRow())
  }*/
}
