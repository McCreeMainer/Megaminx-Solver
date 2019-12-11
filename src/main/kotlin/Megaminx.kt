class Megaminx {

    private val SIDE_COUNT = 12
    private val EDGE_COUNT = 5
    private val MIN_VALUE = 0
    private val MAX_VALUE = 11

    private val ROTATE_LEFT = 1
    private val ROTATE_LEFT_D = 2
    private val ROTATE_RIGHT_D = 3
    private val ROTATE_RIGHT = 4

    private val WHITE = 0
    private val GREY = 1
    private val RED = 2
    private val ORANGE = 3
    private val GREEN = 4
    private val GRASS = 5
    private val PURPLE = 6
    private val PINK = 7
    private val YELLOW = 8
    private val VANILLA = 9
    private val BLUE = 10
    private val AZURE = 11

    private val front = Side(WHITE)
    private val back = Side(GREY)
    private val up = Side(RED)
    private val down = Side(ORANGE)
    private val upRight = Side(GREEN)
    private val downLeft = Side(GRASS)
    private val frontRight = Side(PURPLE)
    private val backLeft = Side(PINK)
    private val frontLeft = Side(YELLOW)
    private val backRight = Side(VANILLA)
    private val upLeft = Side(BLUE)
    private val downRight = Side(AZURE)

    private val arrOfSides = arrayListOf(
        down, back, backLeft, downLeft, upLeft, frontLeft, front, frontRight, upRight, downRight, backRight, up
    )

    fun setPositions(arrOfEdges: ArrayList<ArrayList<Int>>, arrOfAngles: ArrayList<ArrayList<Int>>) {

        front.setSides(arrayListOf(up, upRight, frontRight, frontLeft, upLeft))
        back.setSides(arrayListOf(down, downRight, backRight, backLeft, downLeft))
        up.setSides(arrayListOf(front, upLeft, backLeft, backRight, upRight))
        down.setSides(arrayListOf(back, downLeft, frontLeft, frontRight, downRight))
        upRight.setSides(arrayListOf(up, backRight, downRight, frontRight, front))
        downLeft.setSides(arrayListOf(down, back, backLeft, upLeft, frontLeft))
        frontRight.setSides(arrayListOf(down, frontLeft, front, upRight, downRight))
        backLeft.setSides(arrayListOf(up, upLeft, downLeft, back, backRight))
        frontLeft.setSides(arrayListOf(down, downLeft, upLeft, front, frontRight))
        backRight.setSides(arrayListOf(up, backLeft, back, downRight, upRight))
        upLeft.setSides(arrayListOf(up, front, frontLeft, downLeft, backLeft))
        downRight.setSides(arrayListOf(down, frontRight, upRight, backRight, back))

        for (i in 0..11) {
            arrOfSides[i].setEdges(arrOfEdges[i])
            arrOfSides[i].setAngles(arrOfAngles[i])
        }

    }


    private class Side(val color: Int) {

        val sides = ArrayList<Side>(5)

        val edges = ArrayList<Int>(5)

        val angles = ArrayList<Int>(5)

        fun setSides(arr: ArrayList<Side>) {
            for (i in 0..4) sides[i] = arr[i]
        }

        fun setEdges(arr: ArrayList<Int>) {
            for (i in 0..4) edges[i] = arr[i]
        }

        fun setAngles(arr: ArrayList<Int>) {
            for (i in 0..4) angles[i] = arr[i]
        }

        fun rotate(rotations: Int) {
            val savedEdge = edges
            val savedAngle = angles
            val savedSide = sides.map { it.getNeighbourSide(color) }
            for (i in 0..4) {
                val index = (i + rotations) % 5
                edges[index] = savedEdge[i]
                angles[index] = savedAngle[i]
                sides[index].setNeighbourSide(color, savedSide[i])
            }
        }

        fun getNeighbourSide(neighbourColor: Int) : ArrayList<Int> {
            val line = ArrayList<Int>(3)
            for (i in 0..4) {
                if (sides[(i + 1) % 5].color == neighbourColor) {
                    line[0] = angles[i]
                    line[1] = edges[i + 1]
                    line[2] = angles[i + 1]
                    break
                }
            }
            return line
        }

        fun setNeighbourSide(neighbourColor: Int, line: ArrayList<Int>) {
            for (i in 0..4) {
                if (sides[(i + 1) % 5].color == neighbourColor) {
                    angles[i] = line[0]
                    edges[i + 1] = line[1]
                    angles[i + 1] = line[2]
                    break
                }
            }
        }

        fun equals(other: Side): Boolean {
            for (i in 0..4) {
                if (edges[i] != other.edges[i] || angles[i] != other.angles[i]) return false
            }
            return true
        }

    }

    fun equal(other: Megaminx) : Boolean {
        val arr = other.arrOfSides
        for (i in 0..11) {
            if (!arrOfSides[i].equals(arr[i])) return false
        }
        return true
    }

}