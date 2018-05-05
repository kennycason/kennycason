---
title: ND Vector - C++
author: Kenny Cason
tags: math, c++
---

This is a N-Dimensional Vector class written in C++. While it can be used for any dimension, if you would like to use 3 or less dimensions including rotation functions check out my previous post (<a href="/posts/2010-09-16-vector-3d-structure-rotation-functions-c.html">here</a>)

<b>VectorND.cpp</b><br/>

```cpp
#ifndef __VECTOR_ND_H__
#define __VECTOR_ND_H__

#include "math.h"
#include <string>
#include <iostream>
#include <sstream>
/*
3D Vector and rotation functions.
Rotations about X, Y, Z, and any arbitrary vector
*/

struct VectorND {

    long double* pts;

    int dim;

    VectorND() {
        pts = new long double[0];
        dim = 0;
    }

    VectorND(long double x) {
        pts = new long double[1];
        pts[0] = x;
        dim = 1;
    }

    VectorND(long double x, long double y) {
        pts = new long double[2];
        pts[0] = x;
        pts[1] = y;
        dim = 2;
    }

    VectorND(long double x, long double y, long double z) {
        pts = new long double[3];
        pts[0] = x;
        pts[1] = y;
        pts[2] = z;
        dim = 3;
    }

    VectorND(int _dim, long double* _pts) {
        pts = new long double[_dim];
        for(int i = 0; i < _dim; i++) {
            pts[i] = _pts[i];
        }
        dim = _dim;
    }

    long double get(int i) {
        return pts[i];
    }

    long double* get() {
        return pts;
    }

    void set(int i, long double value) {
        pts[i] = value;
    }


    /**
     * assignment operator
     */
    void operator=(VectorND v) {
        pts = new long double[v.dim];
        for(int i = 0; i < dim; i++) {
            pts[i] = v.get(i);
        }
    }

    /**
     * equality operator
     */
    bool operator==(VectorND v) {
        for(int i = 0; i < dim; i++) {
            if(pts[i] != v.get(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * equality operator
     */
    bool operator!=(VectorND v) {
        for(int i = 0; i < dim; i++) {
            if(pts[i] != v.get(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * addition operators
     */
    void operator+=(VectorND v) {
        for(int i = 0; i < dim; i++) {
            pts[i] += v.get(i);
        }
    }

    VectorND operator+(VectorND v) {
        VectorND vect(v.dim, v.get());
        for(int i = 0; i < dim; i++) {
            vect.set(i, pts[i] + v.get(i));
        }
        return vect;
    }

    void operator++(int) {
        for(int i = 0; i < dim; i++) {
            pts[i]++;
        }
    }

    void operator++() {
        for(int i = 0; i < dim; i++) {
            ++pts[i];
        }
    }

    /**
     * subtraction operators
     */
    void operator-=(VectorND v) {
        for(int i = 0; i < dim; i++) {
            pts[i] -= v.get(i);
        }
    }

    VectorND operator-(VectorND v) {
        VectorND vect(v.dim, v.get());
        for(int i = 0; i < dim; i++) {
            vect.set(i, pts[i] - v.get(i));
        }
        return vect;
    }

    void operator--(int) {
        for(int i = 0; i < dim; i++) {
            pts[i]--;
        }
    }

    void operator--() {
        for(int i = 0; i < dim; i++) {
            --pts[i];
        }
    }

    /**
     * division operators
     */
    void operator/=(long double scalar) {
        for(int i = 0; i < dim; i++) {
            pts[i] /= scalar;
        }
    }

    VectorND operator/(long double scalar) {
        VectorND vect(dim, pts);
        for(int i = 0; i < dim; i++) {
            vect.set(i, pts[i] / scalar);
        }
        return vect;
    }

    /**
     * multiplication operators
     */
    void operator*=(long double scalar) {
        for(int i = 0; i < dim; i++) {
            pts[i] *= scalar;
        }
    }

    VectorND operator*(long double scalar) {
        VectorND vect(dim, pts);
        for(int i = 0; i < dim; i++) {
            vect.set(i, pts[i] * scalar);
        }
        return vect;
    }

    /**
     * exponent operators
     */
    void operator^=(float power) {
        for(int i = 0; i < dim; i++) {
            pts[i] = pow(pts[i], power);
        }
    }

    VectorND operator^(float power){
        VectorND vect(dim, pts);
        for(int i = 0; i < dim; i++) {
            vect.set(i, pow(pts[i], power));
        }
        return vect;
    }

    VectorND unit() {
        VectorND vect(dim,pts);
        double d;
        for(int i = 0; i < dim; i++) {
            d += pts[i] * pts[i];
        }
        d = sqrt(d);
        vect = *(this) / d;
        return vect;
    }

    /**
     * toString()
     */
    std::string toString() {
        std::stringstream oss;
        oss << "(";
        for(int i = 0; i < dim - 1; i++) {
            oss << pts[i] << ", ";
        }
        oss << pts[dim - 1] << ")";
        return oss.str();
    }

};
#endif
```


<b>main.cpp</b><br/>

```cpp
#include <iostream>
#include "VectorND.h";

using namespace std;

int main() {
    cout << "Vector ND" << endl;
    long double* pts = new long double[5]{1,2,3,4,5};
    long double* pts2 = new long double[5]{1,2,3,4,5};
    VectorND v(5, pts);
    VectorND v2(5, pts);
    cout << v.toString() << endl;
    v *= 3;
    cout << v.toString() << endl;
    v /= 3;
    cout << v.toString() << endl;
    v += v2;
    cout << v.toString() << endl;
    v -= v2;
    cout << v.toString() << endl;
    v++;
    cout << v.toString() << endl;
    v--;
    cout << v.toString() << endl;


    cout << (v == v2) << endl;
    cout << (v != v2) << endl;

    cout << (v * 3).toString() << endl;
    cout << (v / 3).toString() << endl;
    cout << (v + v2).toString() << endl;
    cout << (v - v2).toString() << endl;

    cout << (v == v2) << endl;
    cout << (v != v2) << endl;

    cout << (v ^ 4).toString() << endl;
    v ^= 4;
    cout << (v).toString() << endl;
    cout <<  v.unit().toString() << endl;
    return 0;
}
```
