package com.example.test.Data;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by king on 2016/12/20.
 */

public class SourceData {
//    private static LinkedHashSet<String> BITMAP_SOURCE = new LinkedHashSet<>();
//    private static LinkedHashSet<String> TITLE_SOURCE = new LinkedHashSet<>();
//    private static LinkedHashSet<String> URL_SOURCE = new LinkedHashSet<>();
//    private static LinkedHashSet<String> ID_SOURCE = new LinkedHashSet<>();
//    private static LinkedHashSet<String> DRAWER_ITEM = new LinkedHashSet<>();

    private static final SourceData sourceData = new SourceData();
    private final static Vector<String> BITMAP_SOURCE = new Vector<>();
    private final static Vector<String> TITLE_SOURCE = new Vector<>();
    private final static Vector<String> URL_SOURCE = new Vector<>();
    private final static Vector<String> DRAWER_ITEM = new Vector<>();
    private final static Vector<String> ID_SOURCE=new Vector<>();
    private static ArrayList<String> LEFT_MESSAGE =new ArrayList<>();
    private static ArrayList<String> RIGHT_MESSAGE =new ArrayList<>();
    private final String[] judge_first = {"新话题", "新发行", "新加入", "最想要", "高评价"};

    public static SourceData getSourceData() {
        return sourceData;
    }

    public void updateSourceData() {
//        LinkedHashSet<String> bitmapSource = getBitmapSource();
//        LinkedHashSet<String> idSource = getIdSource();
//        LinkedHashSet<String> titleSource = getTitleSource();
//        LinkedHashSet<String> urlSource = getUrlSource();
//        bitmapSource.removeAll(bitmapSource);
//        idSource.removeAll(idSource);
        BITMAP_SOURCE.removeAll(BITMAP_SOURCE);
        TITLE_SOURCE.removeAll(TITLE_SOURCE);
        URL_SOURCE.removeAll(URL_SOURCE);
        ID_SOURCE.removeAll(ID_SOURCE);
        LEFT_MESSAGE.removeAll(LEFT_MESSAGE);
        RIGHT_MESSAGE.removeAll(RIGHT_MESSAGE);
    }

    public void addLeftMessage(String value){
        LEFT_MESSAGE.add(value);
    }

    public void addRightMessage(String value){
        RIGHT_MESSAGE.add(value);
    }

    public void upDateMessage(){
        LEFT_MESSAGE.removeAll(LEFT_MESSAGE);
        RIGHT_MESSAGE.removeAll(RIGHT_MESSAGE);
    }

    public ArrayList<String> getLeftMessage(){
        return LEFT_MESSAGE;
    }

    public ArrayList<String> getRightMessage(){
        return RIGHT_MESSAGE;
    }

    public void addDrawerItem(String item) {
        DRAWER_ITEM.add(item);
    }

    public void addId(String id) {
        ID_SOURCE.add(id);
    }

    public void addBitmap(String bitmap) {
        BITMAP_SOURCE.add(bitmap);
    }

    public void addTitle(String title) {
        TITLE_SOURCE.add(title);
    }

    public void addUrl(String url) {
        URL_SOURCE.add(url);
    }

    public Vector<String> getDrawerItem() {
        return DRAWER_ITEM;
    }

    public Vector<String> getIdSource() {
        return ID_SOURCE;
    }

    public Vector<String> getBitmapSource() {
        return BITMAP_SOURCE;
    }

    public Vector<String> getTitleSource() {
        return TITLE_SOURCE;
    }

    public Vector<String> getUrlSource() {
        return URL_SOURCE;
    }

    public String[] getJudge_first(){
        return judge_first;
    }
}
