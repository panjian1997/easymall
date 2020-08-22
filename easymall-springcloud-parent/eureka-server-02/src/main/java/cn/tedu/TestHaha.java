package cn.tedu;

import com.google.common.annotations.VisibleForTesting;

public class TestHaha {
    public static void main(String[] args) {
        Integer id=new Integer(1);
        System.out.println((id.hashCode()&Integer.MAX_VALUE)%5);
    }
}
