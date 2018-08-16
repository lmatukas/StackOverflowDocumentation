package Services;

import Models.BL.DocTag;
import Models.CONS.Settings;
import Models.DAL.DocTagsDAL;
import Models.DTO.DocTagsDTO;
import Services.Impl.Cache;
import org.modelmapper.ModelMapper;

import javax.print.attribute.standard.Destination;
import java.util.ArrayList;
import java.util.List;

public class DropDown {

    private String getDropDown = Settings.DROPDOWN_CACHE;
    private String[] dropDownsNeeded = Settings.DROPDOWN_LANGUAGES;
    public int getSize() {
        return dropDownsNeeded.length;
    }

    public List<DocTag> getList() {
        Cache cache = Cache.getInstance();
        List<DocTag> docTagsList = new ArrayList<>();
        Object obj = cache.get(getDropDown);
        if (cache.get(getDropDown) != null) {
            docTagsList = (List<DocTag>) obj;
            return docTagsList;
        } else {
            IHigherService higher = DIContainer.getInjector().getInstance(IHigherService.class);
            DocTagsDTO docTagsDTO = higher.getDocTagById(dropDownsNeeded);
            if (docTagsDTO.success) {
                List<DocTagsDAL> docTagsDALsList = docTagsDTO.list;
                for (int i = 0; i < docTagsDALsList.size(); i++) {
                    ModelMapper modelMapper = new ModelMapper();
                    modelMapper.getConfiguration().setFieldMatchingEnabled(true);
                    docTagsList.add(modelMapper.map(docTagsDALsList.get(i) ,DocTag.class));
                }
            }
            cache.put(getDropDown, docTagsList);
            return docTagsList;
        }
    }
}