package sugarya.contrast.chapter2;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class JavaContrast {

    public static void main(String[] args) {

//        List<String>  list  =  new ArrayList<>();
//
//        for(String s : list){
//
//        }
        testRuntimeException();

    }

    private static void testRuntimeException() {
        int i = Integer.parseInt("!");
    }

    private void testCheckedException() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("/temp");

    }
}
