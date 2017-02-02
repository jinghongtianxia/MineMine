package com.example.test.Data;

/**
 * Created by king on 2017/1/16.
 */

public class WebUrl {
    private static final String HOME_PAGE = "http://jav11b.com/";
    private static final String NEW_TOPIC = "vl_update.php";
    private static final String NEW_RELEASE = "vl_newrelease.php";
    private static final String NEW_MOVIES = "vl_newentries.php";
    private static final String MOST_WANTED = "vl_mostwanted.php";
    private static final String MOST_PRAISE = "vl_bestrated.php";
    private static final String VIDEO_CATEGORIES = "genres.php";
    private static final String ACTOR_RANK = "star_mostfav.php";
    private static final String ACTOR_LIST = "star_list.php";

    public static String getHomePage() {
        return HOME_PAGE;
    }

    public static String getNewTopic() {
        return NEW_TOPIC;
    }

    public static String getNewRelease() {
        return NEW_RELEASE;
    }

    public static String getNewMovies() {
        return NEW_MOVIES;
    }

    public static String getMostWanted() {
        return MOST_WANTED;
    }

    public static String getMostPraise() {
        return MOST_PRAISE;
    }

    public static String getVideoCategories() {
        return VIDEO_CATEGORIES;
    }

    public static String getActorRank() {
        return ACTOR_RANK;
    }

    public static String getActorList() {
        return ACTOR_LIST;
    }

}
