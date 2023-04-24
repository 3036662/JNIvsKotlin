//
// Created by aleh on 13.04.23.
//

#include "Mandelbrot.h"
#include "Complex.h"
#include <iostream>
#include <string>

#include <android/log.h>

Mandelbrot::Mandelbrot()
{
    // подготовить vector так как зранее неизвестно количество точек для закрашивания
    points.reserve(300000);
}

std::pair<size_t,double*> Mandelbrot::calc(const double start_x,const double end_x,const double start_y,const double end_y,unsigned int max_iter){
    points.clear();
    // рассчет шага
    const  int HD_WIDTH=1920;
    const  int HD_HEIGHT=1280;
    double step_x=(end_x-start_x)/HD_WIDTH;
    double step_y=(end_y-start_y)/HD_HEIGHT;

    int iterNumb=0;
    // пройти все точки с нужным шагом
    for (double x=start_x;x<=end_x;x+=step_x){
        for (double y=start_y;y<=end_y;y+=step_y){
            Complex cn; // new complex 0
            unsigned int i=0;
            while (i<max_iter && sqr_abs(cn)<=4){
                cn=square(cn)+Complex(x,y);
                ++i;
                ++iterNumb;
            }
            if (sqr_abs(cn)<=4){
                points.push_back(x);
                points.push_back(y);
            }
        }
    }
    __android_log_print(ANDROID_LOG_VERBOSE, "Native VS Kotlin","Iteration number %d",iterNumb);

    return std::make_pair(points.size(),points.data());
}




Mandelbrot::~Mandelbrot()
{

}