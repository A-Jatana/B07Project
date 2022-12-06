package com.example.b07projectapp;

public class SyntaxCheck {

    public static boolean isValidCourse(String text) {

        int i = 0;
        int j = 0;

        char array[] = text.toCharArray();

        while (i<text.length()) {
            char c = array[i];
            if (c == ' ' || c == ',') {
                if (j != 0) {
                    return false;
                }
            } else if (!isNumber(c)&&!isLetter(c)) {
                return false;
            } else {
                if (j >= 0 && j<= 3) { //First three characters of a course code
                    if (isNumber(c)) {
                        return false;
                    }
                } else if (j==4 || j==5) {
                    if (isLetter(c)) {
                        return false;
                    }
                }

                if (j==5) {
                    j=0;
                } else {
                    j++;
                }
            }
            i++;

        }
        return true;
    }

    public static boolean isNumber(char c) {
        if ("1234567890".indexOf(c) == -1) {
            return false;
        }
        return true;
    }

    public static boolean isLetter(char c) {
        if ("ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(c) == -1) {
            return false;
        }
        return true;
    }

    public static String toValidCourse(String text) {
        int i = 0;
        int startIdx = -1;
        int endIdx = -1;
        String s = "";
        char array[] = text.toCharArray();
        int length = 0;
        while (i<text.length()) {
            char c = array[i];
            if (c== ' ' || c == ',') {
                if (startIdx != -1) {
                    endIdx = i;
                }
            } else {
                if (startIdx == -1) {
                    startIdx = i;
                }
                if (i == text.length()-1 || length == 5) {
                    endIdx = i+1;
                    length = 0;
                } else {
                    length++;
                }
            }

            if (startIdx != -1 && endIdx != -1) {

                if (!(s.equals(""))) {
                    s+=",";
                }
                s +=text.substring(startIdx, endIdx);
                startIdx = -1;
                endIdx = -1;
            }
            i++;
        }

        return s;
    }

    public static boolean isValidSession(String text) {
        int i = 0;
        char array[] = text.toCharArray();
        while (i< text.length()) {
            char c = array[i];
            if (("FfSsWw".indexOf(c) == -1) && !(c==' ') && !(c==',')) { //If c is none of the valid letter
                return false;
            } else if (c == 'F' || c == 'f'){
                if (!isFall(text,i)) {
                    return false;
                } else {
                    i += 4;
                }
            } else if (c == 'W' || c=='w') {
                if (!isWinter(text,i)) {
                    return false;
                } else {
                    i += 6;
                }
            } else if (c=='S' || c=='s') {
                if (!isSummer(text,i)) {
                    return false;
                } else {
                    i += 6;
                }
            } else {
                i++;
            }
        }
        return true;
    }

    public static boolean isFall(String text, int i) {
        String sub = text.substring(i);
        if (sub.length()<4) {
            return false;
        } else {
            String fallSub = sub.substring(0, 4).toUpperCase();
            if (!fallSub.equals("FALL")) {
                return false;
            }
        }
        return true;
    }

    public static boolean isWinter(String text, int i) {
        String sub = text.substring(i);
        if (sub.length()<6) {
            return false;
        } else {
            String winterSub = sub.substring(0, 6).toUpperCase();
            if (!winterSub.equals("WINTER")) {
                return false;
            }
        }
        return true;
    }
    public static boolean isSummer(String text, int i) {
        String sub = text.substring(i);
        if (sub.length()<6) {
            return false;
        } else {
            String summerSub = sub.substring(0, 6).toUpperCase();
            if (!summerSub.equals("SUMMER")) {
                return false;
            }
        }
        return true;
    }
    public static String toValidSession(String text) {
        //MAX THREE SESSIONS in order: [F,W,S]
        boolean sessions[] = {false, false, false};
        String sessionNames [] = {"Fall","Winter","Summer"};
        int i = 0;
        char array[] = text.toCharArray();
        while (i< text.length()) {
            char c = array[i];

            if (c == 'F' || c=='f'){
                i += 4;
                sessions[0] = true;
            }
            if (c == 'W' || c== 'w') {
                i+=6;
                sessions[1] = true;
            }
            if (c =='S' || c=='s') {
                i+= 6;
                sessions[2]=true;
            }

            if ("FfSsWw".indexOf(c) == -1) {
                i++;
            }
        }

        String s = "";
        for (int j = 0; j< 3; j++) {
            if (sessions[j] == true) {
                if (!s.equals("")) {
                    s+=",";
                }
                s+= sessionNames[j];
            }
        }
        return s;
    }
}
