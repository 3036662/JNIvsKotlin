package ru.tusur.nativevskotlin1.model


import kotlin.math.pow

class Complex( var real:Double=0.0, var imaginary:Double=0.0) {
    // plus operator
    operator fun plus(b:Complex):Complex {
        return Complex(real+b.real,imaginary+b.imaginary)
    }

    init {
        ++counter
    }

    companion object {
        var counter=0;
    }
}

//square
fun square(b:Complex):Complex{
    return Complex(
        b.real*b.real-b.imaginary*b.imaginary,
        2*b.real*b.imaginary
    )
}

// square of number module
fun sqrAbs(b: Complex):Double{
    return  b.real.pow(2) + b.imaginary.pow(2)
}