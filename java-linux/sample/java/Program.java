public class Program {
        static {
        System.loadLibrary("samplewrapper");
    }

    public static void main(String args[]) {
        cparameter cobj = new cparameter(5, "abc");
        sample sampleobj = new sample(cobj);
        System.out.println("Output is " + sampleobj.times2(5));
    }
}

