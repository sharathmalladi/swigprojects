class cparameter
{
    int cval;
    char* cstr;

public:
    cparameter(int cval, char* cstr);
};

class sample
{
public:
    sample(void);
    sample(const cparameter& cp);
    ~sample(void);

    int times2(int arg);
};