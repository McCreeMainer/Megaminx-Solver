class Tree (start: Megaminx) : AbstractMutableSet<String>(), MutableSet<String> {

    private val FRONT = "FF"
    private val BACK = "BB"
    private val UP = "UU"
    private val DOWN = "DD"
    private val UP_RIGHT = "UR"
    private val DOWN_LEFT = "DL"
    private val FRONT_RIGHT = "FR"
    private val BACK_LEFT = "BL"
    private val FRONT_LEFT = "FL"
    private val BACK_RIGHT = "BR"
    private val UP_LEFT = "UL"
    private val DOWN_RIGHT = "DR"

    private val ROTATE_LEFT = 1
    private val ROTATE_LEFT_DOUBLE = 2
    private val ROTATE_RIGHT_DOUBLE = 3
    private val ROTATE_RIGHT = 4

    private var root = Node("" to 0)

    override var size: Int = 0
        private set

    private class Node(val action: Pair<String, Int>) {

        var combo: Megaminx
            public set

        var right: Node? = null

        val children: MutableMap<Pair<String, Int>, Node> = linkedMapOf()

    }

    override fun clear() {
        root.children.clear()
        size = 0
    }

    private fun find(value: Megaminx): Node? {
        return find(root, value)
    }

    private fun find(start: Node, value: Megaminx): Node? {
        if (value.equal(start.combo)) return start
        else {

        }
        return null
    }

    fun contains(element: Megaminx): Boolean =
        find(element) != null

    override fun add(element: String): Boolean {
        var current = root
        var modified = false
        if (modified) {
            size++
        }
        return modified
    }

    override fun remove(element: String): Boolean {
        TODO()
    }

    override fun iterator(): MutableIterator<String> {
        TODO()
    }
}