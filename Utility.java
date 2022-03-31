package database;

public class Utility {
    public static String printArray(Object[] arr) {
        String str = "";
        for (Object i : arr)
            str += (i.toString() + " ");
        str += "\n";
        return str;
    }
}
