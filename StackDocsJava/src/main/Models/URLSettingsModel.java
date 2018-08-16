package Models;

public class URLSettingsModel {
    public String topicId;
    public String docTagId;
    public String searchQuery;
    public boolean after;

    public URLSettingsModel(String TopicId, String DocTagId, String SearchQuery, boolean After) {
        topicId = TopicId;
        docTagId = DocTagId;
        searchQuery = SearchQuery;
        after = After;
    }
}
