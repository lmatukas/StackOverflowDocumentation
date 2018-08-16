package Services.Impl;

import Models.CONS.Errors;
import Models.DAL.DocTagsDAL;
import Models.DAL.ExamplesDAL;
import Models.DAL.TopicsDAL;
import Models.DBQueryModel;
import Models.DTO.DBqueryDTO;
import Models.DTO.DocTagsDTO;
import Models.DTO.ExampleDTO;
import Models.DTO.TopicsDTO;
import Services.ICache;
import Services.ICrud;
import Services.IHigherService;
import com.google.inject.Inject;

public class HigherService implements IHigherService {

    private ICrud crud;
    private ICache cache;
    private DBQueryModel model;
    
    @Inject
    public HigherService(ICrud iCrud, ICache iCache) {
        crud = iCrud;
        cache = iCache;
    }

    @Override
    public DocTagsDTO getDocTagById(String... docTagIds) {
        model = new DBQueryModel();
        model.where = "id";
        model.whereValue = docTagIds;
        return getDocTagsDTO(crud.read(model, DocTagsDAL.class));
    }

    @Override
    public TopicsDTO getTopicById(String... topicIds) {
        model = new DBQueryModel();
        model.where = "id";
        model.whereValue = topicIds;
        return getTopicsDTO(crud.read(model, TopicsDAL.class));
    }

    @Override
    public ExampleDTO getExampleById(String... exampleIds) {
        model = new DBQueryModel();
        model.where = "id";
        model.whereValue = exampleIds;
        return getExampleDTO(crud.read(model, ExamplesDAL.class));
    }

    @Override
    public TopicsDTO getTopicsByDocTagId(String... docTagIds) {
        model = new DBQueryModel();
        model.where = "DocTagId";
        model.whereValue = docTagIds;
        return getTopicsDTO(crud.read(model, TopicsDAL.class));
    }

    @Override
    public ExampleDTO getExamplesByTopicsId(String... topicIds) {
        model = new DBQueryModel();
        model.where = "DocTopicId";
        model.whereValue = topicIds;
        return getExampleDTO(crud.read(model, ExamplesDAL.class));
    }

    @Override
    public DocTagsDTO getAllDocTags() {
        String cachePlacement = "allDocTags";
        if (cache.get(cachePlacement) != null) return (DocTagsDTO) cache.get(cachePlacement);
        DBqueryDTO<DocTagsDAL> dBqueryDTO = crud.read(new DBQueryModel(), DocTagsDAL.class);
        if (!dBqueryDTO.success) return new DocTagsDTO(false, dBqueryDTO.message,null);
        if (dBqueryDTO.list == null) return new DocTagsDTO(false, Errors.HIGHERSERVICE_ERROR.get(), null);
        DocTagsDTO docTagsDTO = new DocTagsDTO(true, null, dBqueryDTO.list);
        cache.put(cachePlacement, docTagsDTO);
        return docTagsDTO;
    }

    @Override
    public TopicsDTO getAllTopics() {
        String cachePlacement = "allTopics";
        if (cache.get(cachePlacement) != null) return (TopicsDTO) cache.get(cachePlacement);
        DBqueryDTO<TopicsDAL> dBqueryDTO = crud.read(new DBQueryModel(), TopicsDAL.class);
        if (!dBqueryDTO.success) return new TopicsDTO(false, dBqueryDTO.message,null);
        if (dBqueryDTO.list == null) return new TopicsDTO(false, Errors.HIGHERSERVICE_ERROR.get(), null);
        TopicsDTO topicsDTO = new TopicsDTO(true, null, dBqueryDTO.list);
        cache.put(cachePlacement, topicsDTO);
        return topicsDTO;
    }

    @Override
    public ExampleDTO getAllExamples() {
        String cachePlacement = "allDocTags";
        if (cache.get(cachePlacement) != null) return (ExampleDTO) cache.get(cachePlacement);
        DBqueryDTO<ExamplesDAL> dBqueryDTO = crud.read(new DBQueryModel(), ExamplesDAL.class);
        if (!dBqueryDTO.success) return new ExampleDTO(false, dBqueryDTO.message,null);
        if (dBqueryDTO.list == null) return new ExampleDTO(false, Errors.HIGHERSERVICE_ERROR.get(), null);
        ExampleDTO exampleDTO = new ExampleDTO(true, null, dBqueryDTO.list);
        cache.put(cachePlacement, exampleDTO);
        return exampleDTO;
    }

    private DocTagsDTO getDocTagsDTO(DBqueryDTO<DocTagsDAL> dBqueryDTO) {
        if (!dBqueryDTO.success) return new DocTagsDTO(false, dBqueryDTO.message,null);
        if (dBqueryDTO.list == null) return new DocTagsDTO(false, Errors.HIGHERSERVICE_ERROR.get(), null);
        return new DocTagsDTO(true, null, dBqueryDTO.list);
    }

    private TopicsDTO getTopicsDTO(DBqueryDTO<TopicsDAL> dBqueryDTO) {
        if (!dBqueryDTO.success) return new TopicsDTO(false, dBqueryDTO.message,null);
        if (dBqueryDTO.list == null) return new TopicsDTO(false, Errors.HIGHERSERVICE_ERROR.get(), null);
        return new TopicsDTO(true, null, dBqueryDTO.list);
    }

    private ExampleDTO getExampleDTO(DBqueryDTO<ExamplesDAL> dBqueryDTO) {
        if (!dBqueryDTO.success) return new ExampleDTO(false, dBqueryDTO.message,null);
        if (dBqueryDTO.list == null) return new ExampleDTO(false, Errors.HIGHERSERVICE_ERROR.get(), null);
        return new ExampleDTO(true, null, dBqueryDTO.list);
    }
}
