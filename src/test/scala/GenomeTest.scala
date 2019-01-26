import java.io.File

import org.scalatest._

import micit.genome._

class FastaSpec extends FlatSpec with Matchers {
  "FastaOnce" should "Read the Fasta file and return the allele." in {
    val fa = new FastaOnce(new File("chr1.fa"))
    fa.getAllele("chr1", 1) should be('t')
    fa.getAllele("chr1", 25) should be('t')
    fa.getAllele("chr1", 100) should be('c')
    fa.getAllele("chr1", 101) should be('c')
    fa.getAllele("chr2", 25) should be('-')
  }
}