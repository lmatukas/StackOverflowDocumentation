package Services;

import Models.BL.Topic;
import Models.DTO.TopicsDTO;
import Models.URLSettingsModel;
import Services.Impl.Cache;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class Pagination {

    private IHigherService hs;
    private ICache cache = Cache.getInstance();
    private List<String[]> topicsAndDocTagsIds = new ArrayList<>();
    private List<String> collectedIds = new ArrayList<>();
    private List<Topic> topicsList = new ArrayList<>();
    private boolean allConnectionsWithDataBaseIsSuccess;

    public Pagination(){
        hs = DIContainer.getInjector().getInstance(IHigherService.class);
    }

    public List<Topic> getList(URLSettingsModel model) {
        resetValues();
        getListOftopicsAndDocTagsIdsFromDataBaseOrCache();
        reduceListByDocTagIdAndSearchQuery(model.docTagId, model.searchQuery);
        collectTopicsIds(model.topicId, model.after);
        makeListFromColletedIds();
        if (!allConnectionsWithDataBaseIsSuccess) {
            return null;
        }
        return topicsList;
    }

    private void resetValues() {
        collectedIds = new ArrayList<>();
        topicsList = new ArrayList<>();
        allConnectionsWithDataBaseIsSuccess = true;
    }

    private void reduceListByDocTagIdAndSearchQuery(String docTagId, String searchQuery) {

        if ((searchQuery != null && !searchQuery.trim().equals("")) && (docTagId != null && !docTagId.trim().equals(""))) {
            String[] queries = searchQuery.trim().toLowerCase().split(" ");
            TopicsDTO topicsDTO = hs.getAllTopics();
            allConnectionsWithDataBaseIsSuccess = allConnectionsWithDataBaseIsSuccess && topicsDTO.success;
            if (topicsDTO.success) {
                List<String[]> tempList = new ArrayList<>();
                for (int i = 0; i < topicsDTO.list.size(); i++) {
                    for (int j = 0; j < queries.length; j++) {
                        String docId = "" + topicsDTO.list.get(i).docTagId;
                        String title = topicsDTO.list.get(i).title.toLowerCase();
                        String introduction = topicsDTO.list.get(i).introductionMarkdown.toLowerCase();
                        String parameters = topicsDTO.list.get(i).parametersMarkdown.toLowerCase();
                        String remarks = topicsDTO.list.get(i).remarksMarkdown.toLowerCase();
                        String syntax = topicsDTO.list.get(i).syntaxMarkdown.toLowerCase();

                        if(docId.equals(docTagId) && (title.contains(normalizeText((queries[j])))
                                || introduction.contains(normalizeText((queries[j])))
                                || parameters.toLowerCase().contains(normalizeText((queries[j])))
                                || remarks.contains(normalizeText((queries[j])))
                                || syntax.contains(normalizeText((queries[j]))))) {
                            String[] arr = {"" + topicsDTO.list.get(i).id, "" + topicsDTO.list.get(i).docTagId};
                            tempList.add(arr);
                        }
                    }
                }
                topicsAndDocTagsIds = tempList;
            }
        }
        else if (docTagId != null && !docTagId.trim().equals("")) {
            List<String[]> tempList = new ArrayList<>();
            for (int i = 0; i < topicsAndDocTagsIds.size(); i++) {
                if (topicsAndDocTagsIds.get(i)[1].equals(docTagId)) tempList.add(topicsAndDocTagsIds.get(i));
            }
            topicsAndDocTagsIds = tempList;
        }
        else if (searchQuery != null && !searchQuery.trim().equals("")) {
            String[] queries = searchQuery.trim().toLowerCase().split(" ");
            TopicsDTO topicsDTO = hs.getAllTopics();
            allConnectionsWithDataBaseIsSuccess = allConnectionsWithDataBaseIsSuccess && topicsDTO.success;
            if (topicsDTO.success) {
                List<String[]> tempList = new ArrayList<>();
                for (int i = 0; i < topicsDTO.list.size(); i++) {
                    for (int j = 0; j < queries.length; j++) {

                        String title = topicsDTO.list.get(i).title.toLowerCase();
                        String introduction = topicsDTO.list.get(i).introductionMarkdown.toLowerCase();
                        String parameters = topicsDTO.list.get(i).parametersMarkdown.toLowerCase();
                        String remarks = topicsDTO.list.get(i).remarksMarkdown.toLowerCase();
                        String syntax = topicsDTO.list.get(i).syntaxMarkdown.toLowerCase();

                        if (title.contains(normalizeText((queries[j])))
                                || introduction.contains(normalizeText((queries[j])))
                                || parameters.contains(normalizeText((queries[j])))
                                || remarks.contains(normalizeText((queries[j])))
                                || syntax.contains(normalizeText((queries[j])))) {
                            String[] arr = {"" + topicsDTO.list.get(i).id, "" + topicsDTO.list.get(i).docTagId};
                            tempList.add(arr);
                        }
                    }
                }
                topicsAndDocTagsIds = tempList;
            }
        }
    }

    private void collectTopicsIds(String topicId, Boolean after) {
        int counter = 0;
        int indexOfTopicFound = 0;
        if (topicId == null) {
            for (int i = 0; i < topicsAndDocTagsIds.size(); i++) {
                if (counter < 10) {
                    collectedIds.add("" + topicsAndDocTagsIds.get(i)[0]);
                    counter++;
                } else {
                    break;
                }
            }
        } else if (after) {
            //finding index of topic in list
            for (int i = 0; i < topicsAndDocTagsIds.size(); i++) {
                if (topicsAndDocTagsIds.get(i)[0].equals(topicId)) indexOfTopicFound = i;
            }
            if (indexOfTopicFound + 10 < topicsAndDocTagsIds.size()) {
                for (int i = indexOfTopicFound; i < topicsAndDocTagsIds.size(); i++) {
                    if (counter < 10 && i + 10 < topicsAndDocTagsIds.size()) {
                        collectedIds.add("" + topicsAndDocTagsIds.get(i + 10)[0]);
                        counter++;
                    }
                    if (counter == 10) {
                        break;
                    }
                }
            } else {
                for (int i = indexOfTopicFound; i < topicsAndDocTagsIds.size(); i++) {
                    if (counter < 10) {
                        collectedIds.add("" + topicsAndDocTagsIds.get(i)[0]);
                        counter++;
                    } else {
                        break;
                    }
                }
            }
        } else if (!after) {
            //finding index of topic in list
            for (int i = 0; i < topicsAndDocTagsIds.size(); i++) {
                if (topicsAndDocTagsIds.get(i)[0].equals(topicId)) indexOfTopicFound = i;
            }
            if (indexOfTopicFound - 1 >= 0) {
                for (int i = indexOfTopicFound - 10; i < topicsAndDocTagsIds.size(); i++) {
                    if (counter < 10 && i >= 0) {
                        collectedIds.add("" + topicsAndDocTagsIds.get(i)[0]);
                        counter++;
                    }
                    if (counter == 10) {
                        break;
                    }
                }
            } else {
                for (int i = indexOfTopicFound; i < topicsAndDocTagsIds.size(); i++) {
                    if (counter < 10) {
                        collectedIds.add("" + topicsAndDocTagsIds.get(i)[0]);
                        counter++;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    private void makeListFromColletedIds() {
        String[] idsArr = collectedIds.toArray(new String[0]);
        TopicsDTO topicsDTO = hs.getTopicById(idsArr);
        allConnectionsWithDataBaseIsSuccess = allConnectionsWithDataBaseIsSuccess && topicsDTO.success;
        if (topicsDTO.success && topicsDTO.list != null) {
            for (int i = 0; i < topicsDTO.list.size(); i++) {
                ModelMapper modelMapper = new ModelMapper();
                modelMapper.getConfiguration().setFieldMatchingEnabled(true);
                topicsList.add(modelMapper.map(topicsDTO.list.get(i), Topic.class));
            }
        } else {
            topicsList.add(new Topic(0, "No results"));
        }
    }

    private void getListOftopicsAndDocTagsIdsFromDataBaseOrCache() {
        String LIST_PLACEMENT_IN_CACHE = "topicsAndDocTagsIds";
        if (cache.get(LIST_PLACEMENT_IN_CACHE) == null) {
            TopicsDTO tdto = hs.getAllTopics();
            allConnectionsWithDataBaseIsSuccess = tdto.success;
            if (tdto.success) {
                for (int i = 0; i < tdto.list.size(); i++) {
                    String[] arr = {"" + tdto.list.get(i).id, "" + tdto.list.get(i).docTagId};
                    topicsAndDocTagsIds.add(arr);
                    cache.put(LIST_PLACEMENT_IN_CACHE, topicsAndDocTagsIds);
                }
            }
        } else {
            Object obj = cache.get(LIST_PLACEMENT_IN_CACHE);
            topicsAndDocTagsIds = (List<String[]>) obj;
        }
    }

    private String normalizeText(String text) {
        return text != null ? text.trim().toLowerCase() : null;
    }
}
