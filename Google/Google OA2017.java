import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    
    private static Map<YearMonth, Map<String, Integer>> map;
    static class YearMonth {
        private int year;
        private int month;
        public YearMonth(int year, int month){
            this.year = year;
            this.month = month;
        }
    }
    
    static class Log {
        private int year;
        private int month;
        private String catalog;
        private int count;
        public Log(int year, int month, String catalog, int count){
            this.year = year;
            this.month = month;
            this.catalog = catalog;
            this.count = count;
        }
    }
    
    private static int[] getTimeStamp(String[] timeArray) {
        int year = 0, month = 0, date = 0;
        try {
            year = Integer.parseInt(timeArray[0]);
            month = Integer.parseInt(timeArray[1]);
            if (timeArray.length == 3) {
                date = Integer.parseInt(timeArray[2]);
            }
        } catch (NumberFormatException exception) {}
        return new int[]{year, month};
    }
    
    private static int countMonth(int[] timestamp) {
        return timestamp[0] * 12 + timestamp[1];
    }
    
    private static void compareTime(String beginTime, String endTime, String line) {
        String[] inputTimeSeries = line.split(",");
        String logTime = inputTimeSeries[0];
        String[] beginArray = beginTime.split("-");
        String[] endArray = endTime.split("-");
        String[] logArray = logTime.split("-");
        if (beginArray.length != 2 || endArray.length != 2 || logArray.length != 3) {
            throw new IllegalArgumentException("The input timestamp is not valid!");
        }
        int thisCount = 0;
        try {
            thisCount = Integer.parseInt(inputTimeSeries[2]);
        } catch (NumberFormatException exception) {}
        int begin = countMonth(getTimeStamp(beginArray));
        int end = countMonth(getTimeStamp(endArray));
        int[] logTimestamp = getTimeStamp(logArray);
        int log = countMonth(logTimestamp);
        if (begin <= log && log < end) {
            YearMonth ym = new YearMonth(logTimestamp[0], logTimestamp[1]);
            Map<String, Integer> catalogMap = new TreeMap<>();
            int count = 0;
            String catalog = inputTimeSeries[1];
            if (map.containsKey(ym)) {
                catalogMap = map.get(ym);
            } 
            catalogMap.put(catalog, catalogMap.getOrDefault(catalog, 0) + thisCount);
            map.put(ym, catalogMap);
        }
    }
        
    private static void print() {
        for (YearMonth ym : map.keySet()) {
            Map<String, Integer> catalogMap = map.get(ym);
            StringBuilder sb = new StringBuilder();
            sb.append(ym.year);
            sb.append('-');
            if (ym.month < 10) sb.append(0);
            sb.append(ym.month);
            for (Map.Entry<String, Integer> e: catalogMap.entrySet()) {
                sb.append(", ");
                sb.append(e.getKey());
                sb.append(", ");
                sb.append(e.getValue());
            }
            System.out.println(sb.toString());
        }
    }
    
    public static void main(String args[] ) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        int numOfLine = 0;
        String beginTime = null;
        String endTime = null;
        map = new TreeMap<>((a, b) -> (a.year == b.year ? b.month - a.month : b.year - a.year));

        try {
            while((line = reader.readLine()) != null){
                line = line.replaceAll("\\s+","");
                switch (numOfLine) {
                    case 0:
                        String[] inputTimeInterval = line.split(",");
                        if (inputTimeInterval.length != 2) {
                            throw new Exception("The time interval is not valid!");
                        }
                        beginTime = inputTimeInterval[0];
                        endTime = inputTimeInterval[1];
                        numOfLine++;
                        break;
                    case 1:
                        numOfLine++;
                        break;
                    default:
                        //line is larger than 1
                        
                        compareTime(beginTime, endTime, line);
                        numOfLine++;
                        break;
                }
            }
        } catch (IOException exception) {}
        print();
    }
}