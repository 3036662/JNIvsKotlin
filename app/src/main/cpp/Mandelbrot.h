//
// Created by aleh on 13.04.23.
//

#include <vector>
#include <cstddef>


#ifndef NATIVE_VS_KOTLIN_1_MANDELBROT_H
#define NATIVE_VS_KOTLIN_1_MANDELBROT_H
class Mandelbrot
{
public:
    Mandelbrot();
    std::pair<size_t,double*> calc(const int width,const int height,const double start_x,const double end_x,const double start_y,const double end_y,unsigned int max_iter);
    ~Mandelbrot();

private:
    std::vector<double> points;

};
#endif //NATIVE_VS_KOTLIN_1_MANDELBROT_H
