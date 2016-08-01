---
title: Vector 3D structure with Operation overloads and basic functions - C++
author: Kenny Cason
tags: 3d, c++, mathematics, vector
---

This is my Vector3D structure with a set of appropriate operation overloads(-,+,/,*,--,++,etc)  that I use for various math operations as well. Feel free to add upon it or do whatever you like with it. (Update the -- and ++ operators are not correctly overloaded :) )

```cpp
#ifndef __VECTOR_3D_H__
#define __VECTOR_3D_H__

#include "math.h"
#include <string>
#include <iostream>
#include <sstream>
/*
ABOUT: 3D Vector and rotation functions. Rotations about X, Y, Z, and any arbitruary Vector
AUTHOR: Kenny Cason
WEBSITE: kennycason.com
EMAIL: kenneth.cason@gmail.com
DATE: 11-19-2009
*/


#define PI 3.14159265358979323846264338327950288

struct Vector3D {

    long double x;
    long double y;
    long double z;

    Vector3D() {
        x = 0; y = 0; z = 0;
    }

    Vector3D(long double _x, long double _y, long double _z) {
        x = _x; y = _y; z = _z;
    }

    /**
     * assignment operator
     */
    bool operator=(Vector3D v) {
        x = v.x; y = v.y; z = v.z;
    }

    /**
     * equality operator
     */
    bool operator==(Vector3D v) {
        return (x == v.x && y == v.y && z == v.z);
    }

    /**
     * equality operator
     */
    bool operator!=(Vector3D v) {
        return (x != v.x || y != v.y || z != v.z);
    }

    /**
     * addition operators
     */
    bool operator+=(Vector3D v) {
        x += v.x; y += v.y; z += v.z;
    }

    Vector3D operator+(Vector3D v) {
        Vector3D vect;
        vect.x = x + v.x; vect.y = y + v.y; vect.z = z + v.z;
        return vect;
    }

    Vector3D operator++(int) {
        x++; y++; z++;
    }

    Vector3D operator++() {
        ++x; ++y; ++z;
    }

    /**
     * subtraction operators
     */
    bool operator-=(Vector3D v) {
        x -= v.x; y -= v.y; z -= v.z;
    }

    Vector3D operator-(Vector3D v) {
        Vector3D vect;
        vect.x = x - v.x; vect.y = y - v.y; vect.z = z - v.z;
        return vect;
    }

    Vector3D operator--(int) {
        x--; y--; z--;
    }

    Vector3D operator--() {
        --x; --y; --z;
    }

    /**
     * division operators
     */
    bool operator/=(long double scalar) {
        x /= scalar; y /= scalar; z /= scalar;
    }

    Vector3D operator/(long double scalar){
        Vector3D vect;
        vect.x = x / scalar; vect.y = y / scalar; vect.z = z / scalar;
        return vect;
    }

    bool operator/=(Vector3D v) {
        x /= v.x; y /= v.y; z /= v.z;
    }

    Vector3D operator/(Vector3D v){
        Vector3D vect;
        vect.x = x / v.x; vect.y = y / v.y; vect.z = z / v.z;
        return vect;
    }


    /**
     * multiplication operators
     */
    bool operator*=(long double scalar) {
        x *= scalar; y *= scalar; z *= scalar;
    }

    Vector3D operator*(long double scalar){
        Vector3D vect;
        vect.x = x * scalar; vect.y = y * scalar; vect.z = z * scalar;
        return vect;
    }

    bool operator*=(Vector3D v) {
        x *= v.x; y *= v.y; z *= v.z;
    }

    Vector3D operator*(Vector3D v){
        Vector3D vect;
        vect.x = x * v.x; vect.y = y * v.y; vect.z = z * v.z;
        return vect;
    }


    /**
     * exponent operators
     */
    bool operator^=(float power) {
        x = pow(x, power); y = pow(y, power); z = pow(z, power);
    }

    Vector3D operator^(float power){
        Vector3D vect;
        vect.x = pow(x, power); vect.y = pow(y, power); vect.z = pow(z, power);
        return vect;
    }

    /**
     * Return Unit Vector
     */
    Vector3D unit() {
        Vector3D vect;
        double d = sqrt(x * x + y * y +  z * z);
        vect = *(this) / d;
        return vect;
    }

    /**
     * toString()
     */
    std::string toString() {
        std::stringstream oss;
        oss << "(" << x << "," << y << "," << z << ")";
        return oss.str();
    }

};
#endif

```
<b>Example usage of operators: main.cpp (Rotation functions are demonstrated in previous posts)</b>

```cpp
#include <iostream>
#include "Vector3D.h"

using namespace std;

int main() {

    Vector3D v(2,2,2);
    cout << "v1, " << v.toString() << endl;

    Vector3D v2(2,3,4);
    cout << "v2, " << v2.toString() << endl;

    v += v2;

    cout << "v1 += v2, " << v.toString() << endl;

    v ^= 2;

    cout << "v1^=2, " << v.toString() << endl;

    v ^= 0.5;

    cout << "v1^=(1/2), " << v.toString() << endl;

    v--;
    --v;
    v++;
    ++v;

    cout << "v1++,++v1,v--,--v," << v.toString() << endl;

    v -= v2;

    cout << "v1 -= v2," << v.toString() << endl;

    cout << "v1 == v2, " << (v == v2) << endl;
    cout << "v1 != v2, " << (v != v2) << endl;

    cout << "unit vect, " << v2.unit().toString() << endl;

    return 0;
}

```