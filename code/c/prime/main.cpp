#include <iostream>
#include <math.h>
#include <stdlib.h>

/*
 * Author: Kenny Cason
 * Site: www.Ken-Soft.com
 * Date: 2010 July 12
 */
using namespace std;

long long isPrime(long double n);
long long firstNPrimeConsecutiveDigitsOfE(int n);

int main() {
    // Test 1
    // list of numbers to test
    long double n[] = {2, 9, 9.5, 11, 100, 200, 313, 845, -1};
    for(int i = 0; n[i] != -1; i++) {
        cout << "N = " << n[i] << ": ";
        long long x = isPrime(n[i]);
        if(x == 0) {
            cout << "YES, it is prime" << endl;
        } else if(x == -1) {
            cout << "NO, is not an integer!" << endl;
        } else {
            cout << "NO, it is divisible by " << x << endl;
        }
    }
    // Test 2
    cout << "find first 10 prime consecutive digits of E (after decimal point) = ";
    long long x = firstNPrimeConsecutiveDigitsOfE(10);
    if(x != 0) {
        cout << x << endl;
    } else {
        cout << "None found!" << endl;
    }
    return 0;
}

long long isPrime(long double n) {
    // is n an integer ?
    if(n / (long long) n != 1.0) {
        return -1;
    }
    // is n even? (half of the numbers)
    if((long long)n % 2 == 0) {
        return 2;
    }
    long long root = sqrtl(n);
    // is the number divisible by n, such that n > 2 and n <= sqrt(number)?
    // if so then the number is composite
    for(long long i = 3; i <= root; i++) {
        if((long long) n % i == 0) {
            return i;
        }
    }
    return 0; // it is prime!
}


long long firstNPrimeConsecutiveDigitsOfE(int n) {
    // digits of E
    string e = "718281828459045235360287471352662497757247093699959574966967627724076630353547594571382178566427471039765214696027662583599051987042300179465536788199970017293907303350869020922519124447393278376151528708043328091329930789368671274139228900330699954432619290007452098959592011594123241022748454826224105481817678677721527982702501171958165776035496266775371236599283248186573925142949855514151213677801670016546716816053432929075730314665624858096529284294227710593056660625152290924148057080971154284394183468786514254137768605429107972100427165852659466365499841407376086916788875302763395714744088183150429172980530448872623204142686146450491853391242252456266873049153816645571980389769254954815620265879514825083711081877834115982875065863132889045138629182617236646975508533506048851101224630493639563270414761687213012664366779882148613589777475808298280906284062947588200067482382460015446";
    for(int i = 0; i < e.size() - n; i++) {
        long long num = atoll(e.substr(i,n).c_str());
        long long x = isPrime(num);
        if(x == 0) {
            return num;
        }
    }
    return 0;
}
