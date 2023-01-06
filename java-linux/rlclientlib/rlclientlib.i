%module rlclientlib

#undef __cplusplus
#define __cplusplus 201103

%{
    #include "live_model.h"
    #include "rl_string_view.h"
    using namespace reinforcement_learning;
%}
 
%include "future_compat.h"
%include "live_model.h"
%include "configuration.h"
%include "rl_string_view.h"
%include "ranking_response.h"
%include "api_status.h"
%include "std_common.i"
%include "std_vector.i"
%include "std_string.i"
%include "sender.h"
%include "container_iterator.h"


namespace reinforcement_learning {
%template(ccirlsrespv) const_container_iterator<slot_response,std::vector<slot_response>>;
%template(ccirlsrankv) const_container_iterator<slot_ranking,std::vector<slot_ranking>>;
%template(ccirlap) const_container_iterator<action_prob,std::vector<action_prob>>;
}