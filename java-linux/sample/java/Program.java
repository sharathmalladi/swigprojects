public class Program {
        static {
        System.loadLibrary("samplewrapper");
    }

    public static void main(String args[]) {
        sample sampleobj = new sample();
        System.out.println("Output is " + sampleobj.times2(5));
    }
}

