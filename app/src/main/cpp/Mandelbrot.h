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
    std::pair<size_t,double*> calc(const double start_x,const double end_x,const double start_y,const double end_y,unsigned int max_iter);
    ~Mandelbrot();

private:
   // std::vector<double> points;
   std::vector<std::unique_ptr<double>> points;
   std::vector<double> resVector;
};
#endif //NATIVE_VS_KOTLIN_1_MANDELBROT_H
