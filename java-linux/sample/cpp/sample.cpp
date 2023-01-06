#include "sample.h"
#include <stdio.h>


cparameter::cparameter(int cval, char* cstr)
{
    printf("Input parameter is %d", cval);
}

sample::sample(void)
{
}

sample::sample(const cparameter& cp)
{
}

sample::~sample(void)
{
}

int sample::times2(int arg)
{
    printf("Hello World");
    return arg * 2;
}
