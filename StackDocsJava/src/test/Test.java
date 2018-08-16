import Models.BL.DocTag;
import Models.BL.Topic;
import Models.DAL.DocTagsDAL;
import Models.DAL.TopicsDAL;
import Models.DBQueryModel;
import Models.DTO.DBqueryDTO;
import Models.DTO.DocTagsDTO;
import Models.URLSettingsModel;
import Services.*;
import Services.Impl.Cache;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class Test {

    static IDataBase db;
    static ICrud crud;
    static IHigherService higher;

    @org.junit.BeforeClass
    public static void setup() {
        db = DIContainer.getInjector().getInstance(IDataBase.class);
        crud = DIContainer.getInjector().getInstance(ICrud.class);
        higher = DIContainer.getInjector().getInstance(IHigherService.class);
    }

    @org.junit.Test
    public void DbgetConnection() throws Exception {
        assertTrue(db.getConnection() != null);
    }

    @org.junit.Test
    public void AssertDocTagsCollection() {
        DocTagsDTO dto = higher.getAllDocTags();
        assertTrue((dto.list.size() > 0));
    }

    @org.junit.Test
    public void AssertDocTagsIds() {
        int[] ids = {3, 4, 5, 8};
        int counter = 0;
        DocTagsDTO dto = higher.getAllDocTags();
        for (int i = 0; i < dto.list.size(); i++) {
            for (int j = 0; j < ids.length; j++) {
                if (dto.list.get(i).id == ids[j]) {
                    counter++;
                }
            }
        }
        assertTrue((counter == ids.length));
    }

    @org.junit.Test
    public void AssertDocTagsIds2() {
        int[] ids = {3, 4, 5, 8};
        List<DocTagsDTO> dtoArr = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            if (higher.getDocTagById("" + ids[i]).list.get(0) != null) {
                dtoArr.add(higher.getDocTagById("" + ids[i]));

            }
        }
        assertTrue((dtoArr.size() == ids.length));
    }

    @org.junit.Test
    public void AssertDocTagsIds3() {
        String[] ids = {"3", "4", "5", "8"};
        assertTrue((higher.getDocTagById(ids).list.size() == ids.length));
    }

    @org.junit.Test
    public void AssertDropDownCollection() {
        DropDown dropDown = new DropDown();
        dropDown.getList().forEach(el -> System.out.println(el.tag));
        assertTrue(dropDown.getList().size() == 4);
    }

    @org.junit.Test
    public void CheckCache() {
        Cache cache = Cache.getInstance();
        List<DocTag> list = new ArrayList<>();
        DocTag docTag = new DocTag(1, "");
        list.add(docTag);
        cache.put("test", list);
        AbstractList newList = (AbstractList) cache.get("test");
        List<DocTag> newest = new ArrayList<>();
        for (Object item : newList) {
            newest.add((DocTag) item);
        }
        DocTag newDocTag = (DocTag) newList.get(0);
        assertTrue(newDocTag.id == docTag.id && newest.get(0).id == docTag.id);
    }

    @org.junit.Test
    public void AssertListFromPagination() {
        Pagination pg = new Pagination();
        URLSettingsModel model = new URLSettingsModel("5", null, "to", true);
        List<Topic> list = pg.getList(model);
        for (Topic item : list) {
            System.out.println("testinam test: title - " + item.title + ", id - " + item.id);
        }
        assertTrue(pg.getList(model).size() == 10);
    }

    @org.junit.Test
    public void AssertTenTopicsFromDataBase() {
        DBQueryModel query = new DBQueryModel();
        query.table = "Topics";
        query.where = "id";
        query.whereValue = new String[]{"1", "2", "3", "4", "5", "6", "8", "10", "11", "12"};
        DBqueryDTO dto = crud.read(query, TopicsDAL.class);
        assertTrue(dto != null);
    }

    @org.junit.Test
    public void AssertCreateMethod() {
        DocTagsDAL dal = new DocTagsDAL();
        dal.tag = "TAGNAME";
        dal.title = "TITLE";
        dal.creationDate = "CREATION DATE";
        dal.helloWorldDocTopicId = 1111111;
        dal.topicCount = 222222;
        DBqueryDTO dto = crud.create(dal);
        assertTrue(dto.success);
    }

    @org.junit.Test
    public void AssertUpdateMethod() {
        DocTagsDAL dal = new DocTagsDAL();
        dal.id = 1200;
        dal.tag = "TAG CHANGED";
        dal.title = "TITLE CHANGED";
        dal.creationDate = "CREATION DATE CHANGED";
        dal.helloWorldDocTopicId = 666666;
        dal.topicCount = 99999;
        DBqueryDTO dto = crud.update(dal, "Id");
        assertTrue(dto.success);
    }

    @org.junit.Test
    public void AssertDeleteMethod() {
        DBQueryModel delModel = new DBQueryModel();
        delModel.where = "Id";
        delModel.whereValue = new String[]{"1200"};
        DBqueryDTO dto = crud.delete(delModel, DocTagsDAL.class);
        assertTrue(dto.success);
    }

    @org.junit.Test
    public void FindWhatsWrong() {
        DBQueryModel model = new DBQueryModel();
        model.where = "id";
        model.whereValue = new String[]{"4", "5", "6", "7", "8", "9"};
        DBqueryDTO<DocTagsDAL> dto = crud.read(model, DocTagsDAL.class);
        dto.list.forEach(tag -> System.out.println(tag.title));
        DocTagsDTO tagsDTO = higher.getDocTagById("4", "5", "6", "7", "8", "9");
        tagsDTO.list.forEach(tag -> System.out.println(tag.title));
    }
}
