package ru.tusur.nativevskotlin1.model2

data class Complex(var real: Double = 0.0, var im: Double = 0.0) {
    // plus operator
    operator fun plus(b: Complex): Complex {
        return Complex(real + b.real, im + b.im)
    }

    fun squareSelf() {
        val r = real * real - im * im
        val im = 2 * real * im
        real = r
        this.im = im
    }

    // square of number module
    fun sqrAbs() = real * real + im * im

    init {
        ++counter
    }

    companion object {
        var counter=0;
    }
}