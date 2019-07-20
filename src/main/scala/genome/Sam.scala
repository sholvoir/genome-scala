package com.micinfotech.genome

import java.io.{File, PrintWriter}

import scala.io.Source
import scala.collection.mutable
import scala.math.Ordered

/**
  * Sam line
  * @param qname Query template NAME
  * @param flag bitwise FLAG
  * @param rname Reference sequence NAME
  * @param pos 1-based leftmost mapping POSition
  * @param mapq MAPping Quality
  * @param cigar CIGAR string
  * @param rnext Ref. name of the mate/next read
  * @param pnext Position of the mate/next read
  * @param tlen observed Template LENgth
  * @param seq segment SEQuence
  * @param qual ASCII of Phred-scaled base QUALity+33
  */
case class SamRow(qname: String, //0
                  flag: Int, //1
                  rname: String, //2
                  pos: Int, //3
                  mapq: Int, //4
                  cigar: String, //5
                  rnext: String, //6
                  pnext: Int, //7
                  tlen: Int, //8
                  seq: String, //9
                  qual: String) extends Ordered[SamRow] {
  import scala.math.Ordered.orderingToOrdered
  def compare(that: SamRow): Int = (this.rname, this.pos) compare (that.rname, that.pos)
}

case class Sam(header: Seq[String], rows: Iterator[SamRow])

/**
  * some sam file function
  */
object Sam {
  /**
    * get iterator[sam] from a sam file
    * @param file sam file
    * @return iterator[sam]
    */
  def read(file: File) = {
    val lines = Source.fromFile(file).getLines.map(_.trim).buffered
    val header = mutable.ListBuffer.empty[String]
    while (lines.head.startsWith("@")) {
      header.append(lines.head)
      lines.next()
    }
    val rows = lines.map { line =>
      val row = line.split("\t")
      SamRow(row(0), row(1).toInt, row(2), row(3).toInt, row(4).toInt, row(5), row(6), row(7).toInt, row(8).toInt, row(9), row(10))
    }
    Sam(header.toList, rows)
  }

  /**
    * sam file splite
    * @param samfile input sam file
    */
  def fileSplit(samfile: File): Unit = {
    val chrfiles = ChromosomeID.values.toList.map { chromesomeId =>
      val chrname = chromesomeId.toString
      val chrdir = new File(samfile.getParentFile, chrname)
      if (!chrdir.exists()) chrdir.mkdir()
      chrname -> new PrintWriter(new File(chrdir, samfile.getName))
    }.toMap
    try for (line <- Source.fromFile(samfile).getLines) {
      if (line.startsWith("@SQ"))
        chrfiles(line.trim().split("\\s")(1).split(":")(1)).println(line)
      else if (line.startsWith("@"))
        for ((_, chrfile) <- chrfiles) chrfile.println(line)
      else chrfiles(line.split("\\s")(2)).println(line)
    } finally for ((_, chrfile) <- chrfiles) chrfile.close()
  }
}