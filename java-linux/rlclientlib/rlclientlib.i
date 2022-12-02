%module rlclientlib

#undef __cplusplus
#define __cplusplus 201103

%{
    #include "live_model.h"
%}
 
%include "future_compat.h"
%include "action_flags.h"
%include "continuous_action_response.h"
%include "decision_response.h"
%include "err_constants.h"
%include "factory_resolver.h"
%include "future_compat.h"
%include "multi_slot_response.h"
%include "multi_slot_response_detailed.h"
%include "multistep.h"
%include "ranking_response.h"
%include "sender.h"
%include "container_iterator.h"
%include "live_model.h"