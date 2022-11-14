package project;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.lang.Integer.*;
import static java.lang.Character.*;

/**
1427번: 소트 인사이드
아스키코드에서 숫자들은 이미 정렬된 상태기 때문에 파싱을 하지 않아도 됨
*/
public class Main {

    
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] arr = br.readLine().toCharArray();
        Arrays.sort(arr);
        for(int i = 0; i<arr.length/2; i++) {
            char t = arr[i];
            arr[i] = arr[arr.length-i-1];
            arr[arr.length-i-1] = t;
        }
        System.out.println(new String(arr));
    }


}
