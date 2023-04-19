//
// Created by aleh on 13.04.23.
//

#include "Complex.h"
#include <cmath>

//конструктор
Complex::Complex():real(0.0),imaginary(0.0){}    //коструктор по умолчанию
Complex::Complex(double r,double i):real(r),imaginary(i){}  // конструктор

// оператор сложения
Complex Complex::operator+(const Complex& cn) const{
    return Complex{real+cn.real,imaginary+cn.imaginary};
}

// геттеры
double Complex::get_real() const{ return real;}
double Complex::get_imaginary() const{return imaginary;}


// возвращает квадрат комплексного числаo
Complex square(const Complex& cn){
    return Complex{cn.get_real()*cn.get_real()-cn.get_imaginary()*cn.get_imaginary(),2*cn.get_real()*cn.get_imaginary()};
}

// возвращает квадрат модуля комплексного числа real^2+imagine^2
double sqr_abs(const Complex& cn){
    return pow(cn.get_real(),2)+pow(cn.get_imaginary(),2);
}

