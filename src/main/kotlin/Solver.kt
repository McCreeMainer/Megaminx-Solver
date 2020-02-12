class Solver {

    private val solve = Array(120){-1}

    init {
        for (i in 0..119) solve[i] = i
    }

    fun generateTest() {

        val testingCombination = ComboList(Megaminx(solve))
        val megaminx = testingCombination.testingFirst()
        val comboList = ComboList(megaminx)
        println(comboList.search())

    }

}

fun main() {
    Solver().generateTest()
}