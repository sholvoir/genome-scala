package micit.genome

object ChromosomeID extends Enumeration {
  val chr1, chr2, chr3, chr4, chr5, chr6, chr7, chr8, chr9, chr10, chr11, chr12, chr13,
  chr14, chr15, chr16, chr17, chr18, chr19, chr20, chr21, chr22, chrX, chrY, chrM = Value
}

object Assembly {
  lazy val assembledSize = Map(
    "hg18" -> Seq(247249719, 242951149, 199501827, 191273063, 180857866, 170899992, 158821424, 146274826, 140273252, 135374737, 134452384, 132349534,
      114142980, 106368585, 100338915, 88827254, 78774742, 76117153, 63811651, 62435964, 46944323, 49691432, 154913754, 57772954, 16571),
    "hg19" -> Seq(249904550, 243199373, 198022430, 191535534, 180915260, 171115067, 159321559, 146440111, 141696573, 135534747, 135046619, 133851895,
      115169878, 107349540, 102531392, 90354753, 81529607, 78081510, 59380841, 63025520, 48157577, 51304566, 155270560, 59373566, 3675142)
  )
}

object GenomeFunc {
  type Gene = (Char, Char)
  private val wcmap = Map('A' -> 'T', 'T' -> 'A', 'C' -> 'G', 'G' -> 'C', 'N' -> 'N',
    'a' -> 't', 't' -> 'a', 'c' -> 'g', 'g' -> 'c', 'n' -> 'n', '-' -> '-')
  val xx = ('X', 'X')
  val nn = ('N', 'N')
  def exchange(gg: Gene) = (gg._2, gg._1)
  def isnx(gg: Gene) = contains(gg)('N') || contains(gg)('X')
  def isxx(gg: Gene) = gg._1 == 'X' && gg._2 == 'X'
  def isnn(gg: Gene) = gg._1 == 'N' && gg._2 == 'N'
  def isbn(gg: Gene) = gg._1 != 'N' && gg._2 == 'N'
  def isnb(gg: Gene) = gg._1 == 'N' && gg._2 != 'N'
  def isHomo(gg: Gene) = !isnx(gg) && (gg._1 == gg._2)
  def isHete(gg: Gene) = !isnx(gg) && (gg._1 != gg._2)
  def other(gg: Gene)(h: Char) = if (gg._1 == h) gg._2 else gg._1
  def isnx(h: Char) = h == 'N' || h == 'X'
  def isn(h: Char) = h == 'N'
  def isx(h: Char) = h == 'X'
  def wc(h: Char) = wcmap.getOrElse(h, '-')
  def contains(gg: Gene)(h: Char) = gg._1 == h || gg._2 == h
  def consistent(h: Char)(gg: Gene): Boolean = isnx(h) || contains(gg)(h)
  def consistent(hh: Gene)(gg: Gene): Boolean = consistent(hh._1)(gg) && consistent(hh._2)(gg)
}