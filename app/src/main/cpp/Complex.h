//
// Created by aleh on 13.04.23.
//

#ifndef NATIVE_VS_KOTLIN_1_COMPLEX_H
#define NATIVE_VS_KOTLIN_1_COMPLEX_H

class Complex{
private:
    double real;
    double imaginary;
public:
    Complex();
    Complex(double,double);
    Complex operator+(const Complex&) const;
    double get_real() const;
    double get_imaginary() const;
};

Complex square(const Complex& );
double sqr_abs(const Complex&);


#endif //NATIVE_VS_KOTLIN_1_COMPLEX_H
