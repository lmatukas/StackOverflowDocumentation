package Models.CONS;

public final class Settings {
    //Its the amount of size sent to frontEnd, and its equals amount of lines of topics will be displayed after any search
    public static int LIST_SIZE = 10;
    //Its the amount of time items will be saved in Cache.class, now its set to 120 seconds
    public static long LIVE_TIME = 120000;
    //  DROPDOWN to cache KEY
    public static String DROPDOWN_CACHE = "dropdownGet";
    // DROPDOWN languages list
    public static String[] DROPDOWN_LANGUAGES = {"3", "4", "5", "8"};
}