#include <jni.h>
#include <string>
#include <android/log.h>
#include "Mandelbrot.h"

// Константы
const unsigned int SCR_WIDTH = 800;
const unsigned int SCR_HEIGHT = 600;

const double X_START=-2.0;
const double X_END=1.0;
const double Y_START=-1.0;
const double Y_END=1.0;

const unsigned int MAX_ITER=200;// максимальное количество итераций


extern "C" JNIEXPORT jdoubleArray  JNICALL Java_ru_tusur_nativevskotlin1_ui_AppViewModel_arrFromJNI(
        JNIEnv *env,
        jobject  /* this */
        ) {
    // TODO: implement AstringFromJNI()
    //рассчет
    Mandelbrot  fractal;
    // пара -количество элементов в массиве,  указатель на массив точек point
    std::pair<size_t,double*> points;
    points=fractal.calc(X_START,X_END,Y_START,Y_END,MAX_ITER);

    // prepare Array for Java

    jdoubleArray arr=   env->NewDoubleArray(points.first+10);
    __android_log_print(ANDROID_LOG_VERBOSE, "Native VS Kotlin","Created java arr %d",points.first);
    env->SetDoubleArrayRegion(arr,0,points.first,points.second);
    __android_log_print(ANDROID_LOG_VERBOSE, "Native VS Kotlin","Copied %d",points.first);
    //env->ReleaseDoubleArrayElements(arr)

    return arr;
}