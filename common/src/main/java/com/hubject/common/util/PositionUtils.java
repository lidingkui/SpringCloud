package com.hubject.common.util;

public class PositionUtils {

    private static final double R = 1L; // 半径为10的球

    public static double[] LatLong2Vector3(double lat, double lng) {
        // 转为弧度制
        lat = lat * Math.PI / 180L;
        lng = lng * Math.PI / 180L;

        // 利用三角函数假设球半径为1 算出坐标
        double[] position = new double[3];
        position[0] = R * Math.cos(lat) * Math.cos(lng); // x
        position[1] = R * Math.cos(lat) * Math.sin(lng); // y
        position[2] = R * Math.sin(lat); // z
        return position;
    }

    //  4 TEST
//    public static void main(String[] args) {
//        double[] aaa = PositionUtils.LatLong2Vector3(48, 17);
//
//        double[] bbb = PositionUtils.LatLong2Vector3(48.142632, 17.134219);
//        double[] ccc = PositionUtils.LatLong2Vector3(60.632750, 16.993200);
//
//        System.out.println(distance(aaa,bbb));
//        System.out.println(distance(aaa,ccc));
//    }
//    static   double distance(double[] a, double[] b) {
//        double distance = 0;
//        for (int i = 0; i < a.length; i++) {
//            distance += Math.pow(a[i] - b[i], 2);
//        }
//        return Math.sqrt(distance);
//    }
}
