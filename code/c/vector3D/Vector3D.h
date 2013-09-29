#ifndef __VECTOR_3D_H__
#define __VECTOR_3D_H__

#include "math.h"

/*
ABOUT: 3D Vector and rotation functions. Rotations about X, Y, Z, and any arbitruary Vector
AUTHOR: Kenny Cason
WEBSITE: Ken-Soft.com
EMAIL: kenneth.cason@gmail.com
DATE: 11-19-2009
*/


#define PI 3.14159265358979323846264338327950288

struct Vector3D {
    double x;
    double y;
    double z;
};

static Vector3D& rotateX(struct Vector3D &Vector, double theta);
static Vector3D& rotateY(struct Vector3D &vector, double theta);
static Vector3D& rotateZ(struct Vector3D &vector, double theta);
static Vector3D& getUnitVector3D(struct Vector3D &vector);
static Vector3D& rotateAroundVector3D(struct Vector3D &vect1, struct Vector3D &vect2, double theta);
static double degToRad(double deg);

/**
  * [(1      ,0      ,0),
  *  (0      ,cos(a) ,-sin(a)),
  *  (0      ,sin(a) ,cos(a))]
  * @param Vector3D
  * @param theta
  */
Vector3D& rotateX(struct Vector3D &vector, double theta) {
    Vector3D newv;
    newv.x = vector.x;
    newv.y = vector.y * cos(theta) + vector.z * -sin(theta);
    newv.z = vector.y * sin(theta) + vector.z * cos(theta);
    return newv;
}

/**
  * [(cos(a) ,0      ,sin(a)),
  *  (0      ,1      ,0),
  *  (-sin(a),0      ,cos(a))]
  * @param Vector3D
  * @param theta
  */
Vector3D& rotateY(struct Vector3D &vector, double theta) {
    Vector3D newv;
    newv.x = vector.x * cos(theta) + vector.z * sin(theta);
    newv.y = vector.y;
    newv.z = vector.x * -sin(theta) + vector.z * cos(theta);
    return newv;
}

/**
  * [(cos(a),-sin(a),0),
  *  (sin(a),cos(a) ,0),
  *  (0     ,0      ,1)]
  * @param Vector3D
  * @param theta
  */
Vector3D& rotateZ(struct Vector3D &vector, double theta) {
    Vector3D newv;
    newv.x = vector.x * cos(theta) + vector.y * -sin(theta);
    newv.y = vector.x * sin(theta) + vector.y * cos(theta);
    newv.z = vector.z;
    return newv;
}


/**
  * returns a unit vector
  */
Vector3D& getUnitVector3D(struct Vector3D &vector) {
    Vector3D newv;
    double d = sqrt(vector.x * vector.x + vector.y * vector.y +  vector.z * vector.z);
    newv.x = vector.x / d;
    newv.y = vector.y / d;
    newv.z = vector.z / d;
    return newv;
}


/**
  * using quaternions to rotate around an arbirtuary axis
  * Given angle theta in radians and unit vector u = ai + bj + ck or (a,b,c)
  *
  * q0 = cos(r/2),  q1 = sin(r/2) a,  q2 = sin(r/2) b,  q3 = sin(r/2) c
  *
  * Q =
  * [
  *   (q0^2 + q1^2 - q2^2 - q3^2)        2(q1q2 - q0q3)     2(q1q3 + q0q2)
  *   2(q2q1 + q0q3)           (q0^2 - q1^2 + q2^2 - q3^2)  2(q2q3 - q0q1)
  *   2(q3q1 - q0q2)             2(q3q2 + q0q1)     (q0^2 - q1^2 - q2^2 + q3^2)
  * ]
  *
  * Q u = u
  *
  * @param Vector3D 1
  * @param Vector3D 2
  * @param theta
  * @return Vector
  */
Vector3D& rotateAroundVector(Vector3D &vect1, Vector3D &vect2, double theta) {
    Vector3D newv;
    Vector3D unit = getUnitVector3D(vect2);
    //theta = Math.toRadians(theta);
    double q0 = cos(theta/2);
    double q1 = sin(theta/2)*unit.x;
    double q2 = sin(theta/2)*unit.y;
    double q3 = sin(theta/2)*unit.z;

    // column vect
    newv.x = (q0*q0 + q1*q1 - q2*q2 - q3*q3)* +    2*(q2*q1 + q0*q3) * vect1.y +                       2*(q3*q1 - q0*q2) * vect1.z;
    newv.y = 2*(q1*q2 - q0*q3)*vect1.x +             (q0*q0 - q1*q1 + q2*q2 - q3*q3) * vect1.y +       2*(q3*q2 + q0*q1) * vect1.z;
    newv.z = 2*(q1*q3 + q0*q2)*vect1.x +             2*(q2*q3 - q0*q1) * vect1.y +                     (q0*q0 - q1*q1 - q2*q2 + q3*q3) * vect1.z;
    return newv;
}


double degToRad(double deg) {
    return deg * PI / 180;
}

#endif
