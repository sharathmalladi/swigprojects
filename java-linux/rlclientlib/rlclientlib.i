%module rlclientlib

#undef __cplusplus
#define __cplusplus 201103

%{
    #include "live_model.h"
%}
 
%include "future_compat.h"
%include "live_model.h"