package micit.genome

import java.io.File
import scala.io.Source

/**
  * get allete only once from a fasta file.
  * you can only get the allele form chromosome1 to chromosome22, chromosomeX, chromosomeY;
  * from the smaller position to larger position
  * @param file fasta file
  */
class FastaOnce(file: File) {
  private val lines = Source.fromFile(file).getLines().filter(!_.startsWith(";"))
  private var seqName = ""
  private var seqPos = 1
  private var seqStr = ""

  /**
    * get allele by it's postion
    * @param name chromosome name
    * @param pos the position of the allele
    * @return
    */
  def getAllele(name: String, pos: Int) = {
    while ((name != seqName || pos >= seqPos + seqStr.length()) && lines.hasNext) {
      val line = lines.next().trim()
      if (line.startsWith(">")) {
        seqName = line.substring(1).split("""\s""")(0)
        seqPos = 1
        seqStr = ""
      } else {
        seqPos += seqStr.length()
        seqStr = line
      }
    }
    if (pos >= seqPos && pos < seqPos + seqStr.length())
      seqStr(pos - seqPos)
    else '-'
  }
}