#include <stdio.h>
#include <stdlib.h>

#include "linkedlist.h"

int main() {

    LinkedList* ll;
    ll_init(ll);
    printf("size() = %d\n", ll_size(ll));
    printf("isEmpty() = %d\n", ll_isEmpty(ll));
    ll_print(ll);
    NodeData data;
    data.value = 10;
    ll_addLast(ll, data);
    data.value = 20;
    ll_addLast(ll, data);
    data.value = 30;
    ll_addLast(ll, data);
    printf("add 10,20,30 to LinkedList\n");
    printf("size() = %d\n", ll_size(ll));
    printf("isEmpty() = %d\n", ll_isEmpty(ll));
    ll_print(ll);
    printf("get(0) = %d\n", ll_get(ll, 0)->data.value);
    printf("get(1) = %d\n", ll_get(ll, 1)->data.value);
    printf("get(2) = %d\n", ll_get(ll, 2)->data.value);
    printf("remove(1) = %d\n", ll_remove(ll, 1)->data.value);
    ll_print(ll);
    printf("remove(1) = %d\n", ll_remove(ll, 1)->data.value);
    ll_print(ll);
    printf("remove(0) = %d\n", ll_remove(ll, 0)->data.value);
    ll_print(ll);
    if(ll->head == ll->tail) {
        printf("head == tail\n");
    }
    data.value = 30;
    ll_addLast(ll, data);
    data.value = 60;
    ll_addLast(ll, data);
    data.value = 90;
    ll_addLast(ll, data);
    ll_print(ll);
    printf("clear()\n");
    ll_clear(ll);
    ll_print(ll);
    printf("size() = %d\n", ll_size(ll));

    data.value = 30;
    ll_addLast(ll, data);
    data.value = 60;
    ll_addLast(ll, data);
    data.value = 90;
    ll_addLast(ll, data);
    ll_print(ll);
    data.value = 256;
    ll_add(ll,0,data);
    ll_print(ll);
    printf("getFirst() = %d\n", ll_getFirst(ll)->data.value);
    printf("getLast() = %d\n", ll_getLast(ll)->data.value);
    data.value = 500;
    ll_setFirst(ll, data);
    printf("setFirst(500)\n");
    data.value = 1000;
    ll_setLast(ll, data);
    printf("setLast(1000)\n");
    ll_print(ll);
    data.value = 1500;
    ll_addLast(ll, data);
    printf("addFirst(1500)\n");
    data.value = 2000;
    ll_addLast(ll, data);
    printf("addLast(2000)\n");
    ll_print(ll);
    printf("size() = %d\n", ll_size(ll));
    printf("isEmpty() = %d\n", ll_isEmpty(ll));
    ll_destroy(ll);
    printf("destroy()\n");
    return 0;
}
