package com.artushock.vestiacomtestfirst

fun main() {

    val list = listOf(
        Circle(100, 100, 50),
        Circle(50, 50, 20),
        Circle(150, 150, 20),
        Rectangle(100, 110, 40, 15),
        Rectangle(100, 90, 10, 40)
    )

    val screen = Screen(
        "Sample",
        list
    )

    println(screen.toXml())
    screen.moveAll(10, 15)
    println(screen.toXml())
    println("All figures area: ${screen.calculateAreaOfAll()}")

    val bb: BoundingBox = screen.getBoundingBox()
    println("\nBounding box is \n\tminX = ${bb.minX}\n\tminY = ${bb.minY}\n\tmaxX = ${bb.maxX}\n\tmaxY = ${bb.maxY}")

}

class Screen(
    private val name: String,
    private val geometryFigures: List<GeometryFigure>
) : XmlExported {

    override fun toXml(): String {
        val xml = StringBuilder()
        xml.appendLine("<screens>")
        xml.appendLine("\t<screen name=\"$name\">")
        for (figure in geometryFigures) {
            xml.appendLine("\t\t ${figure.toXml()}")
        }
        xml.appendLine("\t</screen>")
        xml.appendLine("</screens>")

        return xml.toString()
    }

    fun moveAll(x: Int, y: Int) {
        for (figure in geometryFigures) {
            figure.move(x, y)
        }
    }

    fun calculateAreaOfAll(): Double {
        var squareSum = 0.0
        for (figure in geometryFigures) {
            squareSum += figure.area()
        }
        return squareSum
    }

    fun getBoundingBox(): BoundingBox {
        var minX = geometryFigures[0].getMinX()
        var minY = geometryFigures[0].getMinY()
        var maxX = geometryFigures[0].getMaxX()
        var maxY = geometryFigures[0].getMaxY()

        for (figure in geometryFigures) {
            if (figure.getMinX() < minX) {
                minX = figure.getMinX()
            }

            if (figure.getMinY() < minY) {
                minY = figure.getMinY()
            }

            if (figure.getMaxX() > maxX) {
                maxX = figure.getMaxX()
            }

            if (figure.getMaxY() > maxY) {
                maxY = figure.getMaxY()
            }
        }

        return BoundingBox(
            minX = minX,
            minY = minY,
            maxX = maxX,
            maxY = maxY
        )
    }
}

interface GeometryFigure : XmlExported {
    fun draw()
    fun move(x: Int, y: Int)
    fun area(): Double
    fun getMinX(): Double
    fun getMinY(): Double
    fun getMaxX(): Double
    fun getMaxY(): Double
}

interface XmlExported {
    fun toXml(): String
}

class Circle(
    private var centerX: Int,
    private var centerY: Int,
    private val radius: Int
) : GeometryFigure {

    override fun draw() {
        println("$name - x: $centerX, y: $centerY, radius: $radius")
    }

    override fun toXml(): String {
        return "<${name.lowercase()} x=\"$centerX\" y=\"$centerY\" radius=\"$radius\">"
    }

    override fun move(x: Int, y: Int) {
        centerX += x
        centerY += y
    }

    override fun area(): Double {
        return 3.14 * radius * radius
    }

    override fun getMinX() = (centerX - radius).toDouble()

    override fun getMinY() = (centerY - radius).toDouble()

    override fun getMaxX() = (centerX + radius).toDouble()

    override fun getMaxY() = (centerY + radius).toDouble()

    companion object {
        const val name = "Circle"
    }
}

class Rectangle(
    private var centerX: Int,
    private var centerY: Int,
    private val width: Int,
    private val height: Int
) : GeometryFigure {

    override fun draw() {
        println("$name - x: $centerX, y: $centerY, width: $width, height: $height")
    }

    override fun toXml(): String {
        return "<${name.lowercase()} x=\"$centerX\" y=\"$centerY\" width=\"$width\" height=\"$height\">"
    }

    override fun move(x: Int, y: Int) {
        centerX += x
        centerY += y
    }

    override fun area(): Double {
        return (width * height).toDouble()
    }

    override fun getMinX() = (centerX - width / 2).toDouble()

    override fun getMinY() = (centerY - height / 2).toDouble()

    override fun getMaxX() = (centerX + width / 2).toDouble()

    override fun getMaxY() = (centerY + height / 2).toDouble()

    companion object {
        const val name = "Rectangle"
    }
}

data class BoundingBox(
    val minX: Double,
    val minY: Double,
    val maxX: Double,
    val maxY: Double,
)