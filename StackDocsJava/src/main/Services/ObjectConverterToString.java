package Services;

import Models.BL.DocTag;
import Models.BL.Example;
import Models.BL.Topic;
import Models.DAL.ExamplesDAL;
import Models.DAL.TopicsDAL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ObjectConverterToString {

    public static List<Map<String, String>> listOfObjectsToStringList(List listOfObjects) {
        List<Map<String, String>> list = new ArrayList<>();
        listOfObjects.forEach(ob -> list.add(objectToStringMap(ob)));
        return list;
    }

    public static Map<String, String> objectToStringMap(Object ob) {
        Map<String, String> map = new HashMap<>();
        if (ob instanceof Topic) {
            Topic tp = (Topic) ob;
            map.put("id", String.valueOf(tp.id));
            map.put("title", tp.title);
        } else if (ob instanceof DocTag) {
            DocTag dt = (DocTag) ob;
            map.put("id", String.valueOf(dt.id));
            map.put("tag", dt.tag);
        } else if (ob instanceof Example) {
            Example ex = (Example) ob;
            map.put("id", String.valueOf(ex.id));
            map.put("bodyHTML", ex.bodyHTML);
        } else if (ob instanceof TopicsDAL) {
            TopicsDAL td = (TopicsDAL) ob;
            map.put("title", td.title);
            map.put("introductionHTML", td.introductionHtml);
            map.put("syntaxHTML", td.syntaxHtml);
            map.put("parametersHTML", td.parametersHtml);
            map.put("remarksHTML", td.remarksHtml);
        } else if (ob instanceof ExamplesDAL) {
            ExamplesDAL ed = (ExamplesDAL) ob;
            map.put("bodyHTML", ed.bodyHtml);
        } else {
            throw new IllegalArgumentException("Can only except Topic, DocTag, Example or their DAL's objects.");
        }
        return map;
    }

    private ObjectConverterToString(){}
}
