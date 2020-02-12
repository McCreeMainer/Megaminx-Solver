data class Megaminx(val gap: Array<Int>) {

    // Rotate side on 72° clockwise
    fun rotate(side: Array<Array<Int>>, rotation: Array<Array<Int>>) {
        for (i in 0..4) {
            val first = gap[side[i][0]]
            for (j in 0..3) {
                gap[side[i][j]] = gap[rotation[i][j]]
            }
            gap[side[i][4]] = first
        }
    }

    fun isSolved(): Boolean {
        for (i in 0..119) {
            if (gap[i] != i) return false
        }
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Megaminx

        if (!gap.contentEquals(other.gap)) return false

        return true
    }

    override fun hashCode(): Int {
        return gap.contentHashCode()
    }

}