#include <iostream>
#include <string>

unsigned long long A(unsigned long long m, unsigned long long n);
std::string getValueTable();
int main() {
    std::cout << "Ackermann Function\n" << getValueTable() << std::endl;
    std::cout << "Testing of values" << std::endl;
    for(int m = 0; m < 5; m++) {
        for(int n = 0; n < 6; n++) {
            std::cout << "A(" << m << ", " << n << ") = " << A(m, n) << std::endl;
        }
    }
    return 0;
}

/**
 * Ackermann function - recursive implementation
 */
unsigned long long A(unsigned long long m, unsigned long long n) {
    if(m == 0) {
        return n + 1;
    }else if(n == 0) {
        return A(m - 1, 1);
    } else {
        return A(m - 1, A(m, n - 1));
    }
}

std::string getValueTable() {
    return "Values taken from: \n\
    http://www-users.cs.york.ac.uk/susan/cyc/a/ackermnn.htm \n\
    A(m,n)  	n = 0  	n = 1  	n = 2  	n = 3  	n = 4  	n = 5\n\
    m = 0 	1 	2 	3 	4 	5 	6\n\
    m = 1 	2 	3 	4 	5 	6 	7\n\
    m = 2 	3 	5 	7 	9 	11 	13\n\
    m = 3 	5 	13 	29 	61 	125 	253\n\
    m = 4 	13 	65533 	265536-3 	2265536-3 	A(3,2265536-3) 	A(3,A(4,4))\n\
    m = 5 	65533 	A(4,65533) 	A(4,A(4,65533)) 	A(4,A(5,2)) 	A(4,A(5,3)) 	A(4,A(5,4))\n\
    m = 6 	A(4,65533) 	A(5,A(4,65533)) 	A(5,A(6,1)) 	A(5,A(6,2)) 	A(5,A(6,3)) 	A(5,A(6,4))";
}
