public class Program {
        static {
        System.loadLibrary("rlclientlib");
    }

    public static void main(String args[]) {
        SWIGTYPE_p_reinforcement_learning__utility__configuration config = null;
        SWIGTYPE_p_std__functionT_void_freinforcement_learning__api_status_const_RF_t error_cb = null;
        live_model model = new live_model(config, error_cb);
    }
}

