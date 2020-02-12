import java.util.*
import kotlin.Comparator
import kotlin.math.max
import kotlin.math.min

class ComboList(start: Megaminx) {

    private val root: Node
    private val goal: Megaminx

    // SIDE - name of rotated side
    // ROTATION - count of rotations
    // ROTATION = 0 - rotate RIGHT on 72°
    // ROTATION = 1 - rotate RIGHT on 144°
    // ROTATION = 2 - rotate LEFT on 144°
    // ROTATION = 3 - rotate LEFT on 72°

    // Names of sides:
    //    0 - U
    //    1 - BL
    //    2 - UL
    //    3 - F
    //    4 - UR
    //    5 - BR
    //    6 - B
    //    7 - DL
    //    8 - FL
    //    9 - FR
    //    A - DR
    //    B - D

    // Arrays of all containing and neighboring elements for each side
    private val sides = arrayOf(
        arrayOf(
            arrayOf(8, 6, 4, 2, 0),
            arrayOf(50, 40, 30, 20, 10),
            arrayOf(48, 38, 28, 18, 58),
            arrayOf(9, 7, 5, 3, 1),
            arrayOf(59, 49, 39, 29, 19)
        ),
        arrayOf(
            arrayOf(18, 16, 14, 12, 10),
            arrayOf(2, 22, 72, 60, 58),
            arrayOf(20, 70, 68, 56, 0),
            arrayOf(19, 17, 15, 13, 11),
            arrayOf(1, 21, 71, 69, 57)
        ),
        arrayOf(
            arrayOf(28, 26, 24, 22, 20),
            arrayOf(4, 32, 82, 70, 18),
            arrayOf(30, 80, 78, 16, 2),
            arrayOf(29, 27, 25, 23, 21),
            arrayOf(3, 31, 81, 79, 17)
        ),
        arrayOf(
            arrayOf(38, 36, 34, 32, 30),
            arrayOf(6, 42, 92, 80, 28),
            arrayOf(40, 90, 88, 26, 4),
            arrayOf(39, 37, 35, 33, 31),
            arrayOf(5, 41, 91, 89, 27)
        ),
        arrayOf(
            arrayOf(48, 46, 44, 42, 40),
            arrayOf(8, 52, 102, 90, 38),
            arrayOf(50, 100, 98, 36, 6),
            arrayOf(49, 47, 45, 43, 41),
            arrayOf(7, 51, 101, 99, 37)
        ),
        arrayOf(
            arrayOf(58, 56, 54, 52, 50),
            arrayOf(0, 12, 62, 100, 48),
            arrayOf(10, 60, 108, 46, 8),
            arrayOf(59, 57, 55, 53, 51),
            arrayOf(9, 11, 61, 109, 47)
        ),
        arrayOf(
            arrayOf(68, 66, 64, 62, 60),
            arrayOf(14, 74, 110, 108, 56),
            arrayOf(72, 118, 106, 54, 12),
            arrayOf(69, 67, 65, 63, 61),
            arrayOf(13, 73, 119, 107, 55)
        ),
        arrayOf(
            arrayOf(78, 76, 74, 72, 70),
            arrayOf(24, 84, 118, 68, 16),
            arrayOf(82, 116, 66, 14, 22),
            arrayOf(79, 77, 75, 73, 71),
            arrayOf(23, 83, 117, 67, 15)
        ),
        arrayOf(
            arrayOf(88, 86, 84, 82, 80),
            arrayOf(34, 94, 116, 78, 26),
            arrayOf(92, 114, 76, 24, 32),
            arrayOf(89, 87, 85, 83, 81),
            arrayOf(33, 93, 115, 77, 25)
        ),
        arrayOf(
            arrayOf(98, 96, 94, 92, 90),
            arrayOf(44, 104, 114, 88, 36),
            arrayOf(102, 112, 86, 34, 42),
            arrayOf(99, 97, 95, 93, 91),
            arrayOf(43, 103, 113, 87, 35)
        ),
        arrayOf(
            arrayOf(108, 106, 104, 102, 100),
            arrayOf(54, 64, 112, 98, 46),
            arrayOf(62, 110, 96, 44, 52),
            arrayOf(109, 107, 105, 103, 101),
            arrayOf(53, 63, 111, 97, 45)
        ),
        arrayOf(
            arrayOf(118, 116, 114, 112, 110),
            arrayOf(66, 76, 86, 96, 106),
            arrayOf(74, 84, 94, 104, 64),
            arrayOf(119, 117, 115, 113, 111),
            arrayOf(65, 75, 85, 95, 105)
        )
    )

    // Arrays for changing of elements during rotating
    private val rotations = arrayOf(
        arrayOf(
            arrayOf(6, 4, 2, 0, 8),
            arrayOf(40, 30, 20, 10, 50),
            arrayOf(38, 28, 18, 58, 48),
            arrayOf(7, 5, 3, 1, 9),
            arrayOf(49, 39, 29, 19, 59)
        ),
        arrayOf(
            arrayOf(16, 14, 12, 10, 18),
            arrayOf(22, 72, 60, 58, 2),
            arrayOf(70, 68, 56, 0, 20),
            arrayOf(17, 15, 13, 11, 19),
            arrayOf(21, 71, 69, 57, 1)
        ),
        arrayOf(
            arrayOf(26, 24, 22, 20, 28),
            arrayOf(32, 82, 70, 18, 4),
            arrayOf(80, 78, 16, 2, 30),
            arrayOf(27, 25, 23, 21, 29),
            arrayOf(31, 81, 79, 17, 3)
        ),
        arrayOf(
            arrayOf(36, 34, 32, 30, 38),
            arrayOf(42, 92, 80, 28, 6),
            arrayOf(90, 88, 26, 4, 40),
            arrayOf(37, 35, 33, 31, 39),
            arrayOf(41, 91, 89, 27, 5)
        ),
        arrayOf(
            arrayOf(46, 44, 42, 40, 48),
            arrayOf(52, 102, 90, 38, 8),
            arrayOf(100, 98, 36, 6, 50),
            arrayOf(47, 45, 43, 41, 49),
            arrayOf(51, 101, 99, 37, 7)
        ),
        arrayOf(
            arrayOf(56, 54, 52, 50, 58),
            arrayOf(12, 62, 100, 48, 0),
            arrayOf(60, 108, 46, 8, 10),
            arrayOf(57, 55, 53, 51, 59),
            arrayOf(11, 61, 109, 47, 9)
        ),
        arrayOf(
            arrayOf(66, 64, 62, 60, 68),
            arrayOf(74, 110, 108, 56, 14),
            arrayOf(118, 106, 54, 12, 72),
            arrayOf(67, 65, 63, 61, 69),
            arrayOf(73, 119, 107, 55, 13)
        ),
        arrayOf(
            arrayOf(76, 74, 72, 70, 78),
            arrayOf(84, 118, 68, 16, 24),
            arrayOf(116, 66, 14, 22, 82),
            arrayOf(77, 75, 73, 71, 79),
            arrayOf(83, 117, 67, 15, 23)
        ),
        arrayOf(
            arrayOf(86, 84, 82, 80, 88),
            arrayOf(94, 116, 78, 26, 34),
            arrayOf(114, 76, 24, 32, 92),
            arrayOf(87, 85, 83, 81, 89),
            arrayOf(93, 115, 77, 25, 33)
        ),
        arrayOf(
            arrayOf(96, 94, 92, 90, 98),
            arrayOf(104, 114, 88, 36, 44),
            arrayOf(112, 86, 34, 42, 102),
            arrayOf(97, 95, 93, 91, 99),
            arrayOf(103, 113, 87, 35, 43)
        ),
        arrayOf(
            arrayOf(106, 104, 102, 100, 108),
            arrayOf(64, 112, 98, 46, 54),
            arrayOf(110, 96, 44, 52, 62),
            arrayOf(107, 105, 103, 101, 109),
            arrayOf(63, 111, 97, 45, 53)
        ),
        arrayOf(
            arrayOf(116, 114, 112, 110, 118),
            arrayOf(76, 86, 96, 106, 66),
            arrayOf(84, 94, 104, 64, 74),
            arrayOf(117, 115, 113, 111, 119),
            arrayOf(75, 85, 95, 105, 65)
        )
    )

    private val corners: Array<Array<Int>> = arrayOf(
        arrayOf(0, 10, 58), arrayOf(2, 20, 18), arrayOf(4, 30, 28), arrayOf(6, 40, 38), arrayOf(8, 50, 48),
        arrayOf(12, 60, 56), arrayOf(14, 72, 68), arrayOf(16, 22, 70), arrayOf(24, 82, 78), arrayOf(26, 32, 80),
        arrayOf(34, 92, 88), arrayOf(36, 42, 90), arrayOf(44, 102, 98), arrayOf(46, 52, 100), arrayOf(54, 62, 108),
        arrayOf(64, 110, 106), arrayOf(66, 74, 118), arrayOf(76, 84, 116), arrayOf(86, 94, 114), arrayOf(96, 104, 112)
    )

    private val edges1: Array<Pair<Int, Int>> = arrayOf(
        9 to 59, 7 to 49, 5 to 39, 3 to 29, 1 to 19, 17 to 21, 23 to 79, 25 to 81, 27 to 31, 33 to 89, 35 to 91,
        37 to 41, 43 to 99, 47 to 51, 87 to 93
    )

    private val edges2: Array<Pair<Int, Int>> = arrayOf(
        11 to 57, 13 to 69, 15 to 71, 45 to 101, 53 to 109, 55 to 61, 63 to 107, 65 to 119, 67 to 73, 75 to 117,
        77 to 83, 85 to 115, 95 to 113, 97 to 103, 105 to 111
    )

    // Array of sides neighbours
    private val neighbors = arrayOf(
        arrayOf(0x1, 0x2, 0x3, 0x4, 0x5),
        arrayOf(0x0, 0x2, 0x5, 0x6, 0x7),
        arrayOf(0x0, 0x1, 0x3, 0x7, 0x8),
        arrayOf(0x0, 0x2, 0x4, 0x8, 0x9),
        arrayOf(0x0, 0x3, 0x5, 0x9, 0xA),
        arrayOf(0x0, 0x1, 0x4, 0x6, 0xA),
        arrayOf(0x1, 0x5, 0x7, 0xA, 0xB),
        arrayOf(0x1, 0x2, 0x6, 0x8, 0xB),
        arrayOf(0x2, 0x3, 0x7, 0x9, 0xB),
        arrayOf(0x3, 0x4, 0x8, 0xA, 0xB),
        arrayOf(0x4, 0x5, 0x6, 0x9, 0xB),
        arrayOf(0x6, 0x7, 0x8, 0x9, 0xA)
    )

    // Array of the opposite sides (index to side)
    private val mirror = arrayOf(0xB, 0x9, 0xA, 0x6, 0x7, 0x8, 0x3, 0x4, 0x5, 0x1, 0x2, 0x0)

    // Array of doublets of commutative sides
    private val doublets = arrayOf(
        0x0 to 0x6, 0x0 to 0x7, 0x0 to 0x8, 0x0 to 0x9, 0x0 to 0xA,
        0x1 to 0x3, 0x1 to 0x4, 0x1 to 0x8, 0x1 to 0xA, 0x1 to 0xB,
        0x2 to 0x4, 0x2 to 0x5, 0x2 to 0x6, 0x2 to 0x9, 0x2 to 0xB,
        0x3 to 0x5, 0x3 to 0x7, 0x3 to 0xA, 0x3 to 0xB,
        0x4 to 0x6, 0x4 to 0x8, 0x4 to 0xB,
        0x5 to 0x7, 0x5 to 0x9, 0x5 to 0xB,
        0x6 to 0x8, 0x6 to 0x9,
        0x7 to 0x9, 0x7 to 0xA,
        0x8 to 0xA
    )

    // Array of triplets of commutative sides
    private val triplets = arrayOf(
        arrayOf(0x0, 0x6, 0x8),
        arrayOf(0x0, 0x6, 0x9),
        arrayOf(0x0, 0x7, 0x9),
        arrayOf(0x0, 0x7, 0xA),
        arrayOf(0x0, 0x8, 0xA),
        arrayOf(0x1, 0x3, 0xA),
        arrayOf(0x1, 0x3, 0xB),
        arrayOf(0x1, 0x4, 0x8),
        arrayOf(0x1, 0x4, 0xB),
        arrayOf(0x1, 0x8, 0xA),
        arrayOf(0x2, 0x4, 0x6),
        arrayOf(0x2, 0x4, 0xB),
        arrayOf(0x2, 0x5, 0xB),
        arrayOf(0x2, 0x5, 0x9),
        arrayOf(0x2, 0x6, 0x9),
        arrayOf(0x3, 0x5, 0x7),
        arrayOf(0x3, 0x5, 0xB),
        arrayOf(0x3, 0x7, 0xA),
        arrayOf(0x4, 0x6, 0x8),
        arrayOf(0x5, 0x7, 0x9)
    )

    init {
        val solvedGap = Array(120) { -1 }
        for (i in 0..119) solvedGap[i] = i
        goal = Megaminx(solvedGap)
        root = Node(start, emptyList(), emptyList())
    }

    inner class Node(val megaminx: Megaminx, sides: List<Int>, rotations: List<Int>) {

        var priority = distanceEvaluation(megaminx, goal)

//        val g = distanceEvaluation(start, megaminx)

        val h = distanceEvaluation(megaminx, goal)

        val f = h

        val combo = Pair(sides, rotations)

        private val children: MutableMap<Int, MutableList<Node>> = mutableMapOf()

        fun add(side: Int, node: Node) {
            if (children[side] == null) children[side] = mutableListOf(node)
            else children[side]!!.add(node)
        }

        fun get(side: Int, rotation: Int): Node {
            return children[side]!![rotation]
        }
    }

    class PriorityQueueComparator : Comparator<Node> {
        override fun compare(o1: Node, o2: Node): Int {
            return o1.f - o2.f
        }
    }

    fun search() : Pair<List<Int>, List<Int>> {

        var result = true

        val priorityQueueComparator = PriorityQueueComparator()
        val listOpen = PriorityQueue<Node>(priorityQueueComparator)


        var threshold = root.h
        var newThreshold = threshold

        listOpen.offer(root)

        while (result) {

            val recorderList = mutableListOf<Array<Int>>()

            threshold = min(newThreshold, threshold)

            newThreshold = 999

            while (listOpen.isNotEmpty()) {

                val current = listOpen.remove()


                if (current.megaminx.isSolved()) {

                    return current.combo
                    result = false
                    break
                }

                for (next in nextStep(current)) {

                    val currentGap = Megaminx(current.megaminx.gap.copyOf())
                    val comboSide = current.combo.first + next
                    for (rotation in 0..3) {
                        val comboRotation = current.combo.second + rotation
                        currentGap.rotate(sides[next], rotations[next])
                        val currentNode = Node(Megaminx(currentGap.gap.copyOf()), comboSide, comboRotation)

                        if (currentNode.f >= threshold) {
                            newThreshold = min(currentNode.f, newThreshold)
                        } else if (!recorderList.any { it contentEquals currentNode.megaminx.gap } && currentNode.combo.first.size < threshold / 5) {

                            println(currentNode.combo)
                            println(currentNode.h)

                            current.add(next, currentNode)
                            listOpen.offer(currentNode)
                        }
                    }
                }
            }
        }
        return Pair(listOf(), listOf())
    }

    fun distanceEvaluation(current: Megaminx, target: Megaminx): Int {
        return max(
            distanceCorners(current.gap, target.gap),
            max(distanceEdges1(current.gap, target.gap), distanceEdges2(current.gap, target.gap))
        )
    }

    private fun distanceCorners(currentGap: Array<Int>, targetGap: Array<Int>): Int {

        var evaluation = 0

        for (it in corners) if (
            currentGap[it[0]] != targetGap[it[0]]
            || currentGap[it[1]] != targetGap[it[1]]
            || currentGap[it[2]] != targetGap[it[2]]
        ) {

            val currentPosition = arrayOf(-1, -1, -1)
            val targetPosition = arrayOf(-1, -1, -1)

            for (i in 0..119) if (currentGap[i] == it[0]) {
                currentPosition[0] = i
                val position = sides[i / 10]
                for (j in 0..4) {
                    if (currentPosition[0] == position[0][j]) {
                        currentPosition[1] = position[1][j]
                        currentPosition[2] = position[2][j]
                        break
                    }
                }
                break
            }

            for (i in 0..119) if (targetGap[i] == it[0]) {
                targetPosition[0] = i
                val position = sides[i / 10]
                for (j in 0..4) {
                    if (targetPosition[0] == position[0][j]) {
                        targetPosition[1] = position[1][j]
                        targetPosition[2] = position[2][j]
                        break
                    }
                }
                break
            }

            var distance = 1
            val frontier: Queue<Int> = LinkedList<Int>()
            val cameFrom = mutableMapOf<Int, Int>()
            for (pos in currentPosition) {
                frontier.offer(pos)
                cameFrom[pos] = -1
            }

            while (frontier.isNotEmpty()) {

                val current = frontier.poll()
                val position = sides[current / 10]

                if (targetPosition.contains(targetGap[currentGap.indexOf(current)])) {
                    var prev = cameFrom[current]
                    while (prev != -1) {
                        prev = cameFrom[prev]
                        distance++
                    }
                    break
                }

                var i = position[0].indexOf(current) + 1
                if (i > 4) i = 0
                for (n in 0..2) {
                    val node = currentGap[position[n][i]]
                    if (!cameFrom.containsKey(node)) {
                        frontier.offer(node)
                        cameFrom[node] = current
                    }
                }

                var j = position[0].indexOf(current) - 1
                if (j < 0) j = 4
                for (n in 0..2) {
                    val node = currentGap[position[n][j]]
                    if (!cameFrom.containsKey(node)) {
                        frontier.offer(node)
                        cameFrom[node] = current
                    }
                }
            }

            evaluation += distance
        }

        return evaluation
    }

    private fun distanceEdges1(currentGap: Array<Int>, targetGap: Array<Int>): Int {
        return distanceEdges(currentGap, targetGap, edges1)
    }

    private fun distanceEdges2(currentGap: Array<Int>, targetGap: Array<Int>): Int {
        return distanceEdges(currentGap, targetGap, edges2)
    }

    private fun distanceEdges(currentGap: Array<Int>, targetGap: Array<Int>, edges: Array<Pair<Int, Int>>): Int {

        var evaluation = 0

        for (it in edges) if (
            currentGap[it.first] != targetGap[it.first]
            || currentGap[it.second] != targetGap[it.second]
        ) {

            val currentPosition = arrayOf(-1, -1)
            val targetPosition = arrayOf(-1, -1)

            for (i in 0..119) if (currentGap[i] == it.first) {
                currentPosition[0] = i
                val position = sides[i / 10]
                for (j in 0..4) {
                    if (currentPosition[0] == position[3][j]) {
                        currentPosition[1] = position[4][j]
                        break
                    }
                }
                break
            }

            for (i in 0..119) if (targetGap[i] == it.first) {
                targetPosition[0] = i
                val position = sides[i / 10]
                for (j in 0..4) {
                    if (targetPosition[0] == position[3][j]) {
                        targetPosition[1] = position[4][j]
                        break
                    }
                }
                break
            }

            val frontier: Queue<Int> = LinkedList<Int>()
            val cameFrom = mutableMapOf<Int, Int>()
            var distance = 0
            for (pos in currentPosition) {
                frontier.offer(pos)
                cameFrom[pos] = -1
            }

            while (frontier.isNotEmpty()) {

                val current = frontier.poll()
                val position = sides[current / 10]

                if (targetPosition.contains(targetGap[currentGap.indexOf(current)])) {
                    var prev = cameFrom[current]
                    while (prev != -1) {
                        prev = cameFrom[prev]
                        distance++
                    }
                    break
                }

                var i = position[3].indexOf(current) + 1
                if (i > 4) i = 0
                for (n in 3..4) {
                    val node = currentGap[position[n][i]]
                    if (!cameFrom.containsKey(node)) {
                        frontier.offer(node)
                        cameFrom[node] = current
                    }
                }

                var j = position[3].indexOf(current) - 1
                if (j < 0) j = 4
                for (n in 3..4) {
                    val node = currentGap[position[n][j]]
                    if (!cameFrom.containsKey(node)) {
                        frontier.offer(node)
                        cameFrom[node] = current
                    }
                }
            }

            evaluation += distance
        }

        return evaluation
    }

    fun nextStep(node: Node): MutableSet<Int> {

        val comboSides = node.combo.first
        val acceptedSides: MutableSet<Int>

        // On Each step compiled a set of combinations that do not commute with previous each side in four ways
        // To count count of combinations use the formula of Herbert Kociemba:
        // a0(0) = 1, a1(0) = 0, a2a(0) = 0, a2b(0) = 0, a3(0) = 0
        // n >= 0
        // a0(n+1) = 0
        // a1(n+1) = 4 * (12 * a0(n) + 5 * a1(n) + 2 * a2a(n))
        // a2a(n+1) = 4 * (5 * a1(n) + 4 * a2a(n) + 10 * a2b(n) + 3 * a3(n)) / 2
        // a2b(n+1) = 4 * (1 * a1(n) + 2 * a2a(n) + 3 * a3(n)) / 2
        // a3(n+1) = 4 * (2 * a2a(n) + 3 * a3(n)) / 3
        when (comboSides.size) {

            // 1 step
            // Rotates each side in four ways
            // 48 combinations
            0 -> {
                acceptedSides = mutableSetOf(0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xA, 0xB)
            }

            // 2 step
            // Only one previous move counts
            // 1536 combinations
            1 -> {
                acceptedSides = oneRecently(comboSides.last())
            }

            // 3 step
            // Two or one previous moves counts
            // 43520 combinations
            2 -> {
                acceptedSides = twoRecently(comboSides)
                if (acceptedSides.isEmpty()) acceptedSides.addAll(oneRecently(comboSides.last()))
            }

            // Next steps are identical
            // Three, two or one previous moves counts
            // 1182720, 31641600, 841318400, 22315008000, 591298560000, 15661924352000, ... combinations
            else -> {
                acceptedSides = threeRecently(comboSides)
                if (acceptedSides.isEmpty()) acceptedSides.addAll(twoRecently(comboSides))
                if (acceptedSides.isEmpty()) acceptedSides.addAll(oneRecently(comboSides.last()))
            }

        }

        return acceptedSides
    }

    private fun oneRecently(combo: Int): MutableSet<Int> {

        val sides = mutableSetOf<Int>()

        // From x1 move

        // To x1 move
        sides.addAll(neighbors[combo])

        // To x2 opposite moves
        if (combo < mirror[combo]) sides.add(mirror[combo])

        // To x2 non-opposite moves
        for (doublet in doublets) if (combo == doublet.first) sides.add(doublet.second)

        return sides
    }

    private fun twoRecently(combo: List<Int>): MutableSet<Int> {

        val sides = mutableSetOf<Int>()

        val secondCombo = combo.last()

        var skip = false

        for (i in combo.size - 2 downTo 0) {

            val firstCombo = combo[i]

            for (prev in combo.size - 1 downTo i + 1) if (neighbors[combo[prev]].contains(firstCombo)
                || combo[prev] == firstCombo
            ) {
                skip = true
                break
            }
            if (skip) {
                skip = false
                continue
            }

            // From x2 opposite moves
            if (firstCombo == mirror[secondCombo] && firstCombo < secondCombo) {

                // To x2 non-opposite moves
                for (doublet in doublets)
                    if (secondCombo == doublet.first || firstCombo == doublet.first)
                        sides.add(doublet.second)

                return sides

            } else for (doublet in doublets) {

                // From x2 non-opposite moves
                if (doublet.first == firstCombo && doublet.second == secondCombo) {

                    // To x3 moves
                    for (triple in triplets) if (firstCombo == triple[0] && secondCombo == triple[1])
                        sides.add(triple[2])

                    // To x2 opposite moves
                    if (firstCombo < mirror[firstCombo]) sides.add(mirror[firstCombo])
                    if (secondCombo < mirror[secondCombo]) sides.add(mirror[secondCombo])

                    // To x2 non-opposite moves
                    for (doublet in doublets)
                        if ((secondCombo == doublet.first && neighbors[firstCombo].contains(doublet.second))
                            || (firstCombo == doublet.first && neighbors[secondCombo].contains(doublet.second))
                        )
                            sides.add(doublet.second)

                    // To x1 move
                    sides.addAll(neighbors[secondCombo].intersect(neighbors[firstCombo].toList()))

                    return sides
                }
            }
        }

        return sides
    }

    private fun threeRecently(combo: List<Int>): MutableSet<Int> {

        val sides = mutableSetOf<Int>()

        val thirdCombo = combo.last()

        var skip = false

        for (i in combo.size - 2 downTo 1) {

            val secondCombo = combo[i]

            for (prev in combo.size - 1 downTo i + 1) if (neighbors[combo[prev]].contains(secondCombo)
                || combo[prev] == secondCombo
            ) {
                skip = true
                break
            }
            if (skip) {
                skip = false
                continue
            }

            for (j in i - 1 downTo 0) {

                val firstCombo = combo[j]

                for (prev in combo.size - 1 downTo j + 1) if (neighbors[combo[prev]].contains(firstCombo)
                    || combo[prev] == firstCombo
                ) {
                    skip = true
                    break
                }
                if (skip) {
                    skip = false
                    continue
                }

                // From x3 moves
                for (triple in triplets) if (firstCombo == triple[0] && secondCombo == triple[1] && thirdCombo == triple[2]) {

                    // To x3 moves
                    for (triple in triplets) {
                        if (
                            triple[0] == firstCombo && triple[1] == secondCombo && triple[2] != thirdCombo
                            || triple[0] == firstCombo && triple[1] == thirdCombo && triple[2] != secondCombo
                            || triple[0] == secondCombo && triple[1] == thirdCombo && triple[2] != firstCombo
                        )
                            sides.add(triple[2])
                    }

                    // To x2 opposite moves
                    if (firstCombo < mirror[firstCombo]) sides.add(mirror[firstCombo])
                    if (secondCombo < mirror[secondCombo]) sides.add(mirror[secondCombo])
                    if (thirdCombo < mirror[thirdCombo]) sides.add(mirror[thirdCombo])

                    // To x2 non-opposite moves
                    for (doublet in doublets)
                        if (
                            (firstCombo == doublet.first && neighbors[secondCombo].contains(doublet.second)
                                    && neighbors[thirdCombo].contains(doublet.second))
                            || (secondCombo == doublet.first && neighbors[firstCombo].contains(doublet.second)
                                    && neighbors[thirdCombo].contains(doublet.second))
                            || (thirdCombo == doublet.first && neighbors[firstCombo].contains(doublet.second)
                                    && neighbors[secondCombo].contains(doublet.second))
                        ) sides.add(doublet.second)

                    return sides
                }
            }
        }

        return sides
    }

    fun testingFirst() : Megaminx {
        val current = root.megaminx.copy()
        current.rotate(sides[0xA], rotations[0xA])
        current.rotate(sides[0xA], rotations[0xA])
        current.rotate(sides[0xA], rotations[0xA])
        current.rotate(sides[0xA], rotations[0xA])
        current.rotate(sides[0x9], rotations[0x9])
        current.rotate(sides[0x9], rotations[0x9])
        current.rotate(sides[0x9], rotations[0x9])
        current.rotate(sides[0x6], rotations[0x6])
        current.rotate(sides[0x6], rotations[0x6])
        current.rotate(sides[0x3], rotations[0x3])
        current.rotate(sides[0x3], rotations[0x3])

        return root.megaminx.copy()

    }


}

