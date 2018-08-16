package Models.DTO;

import Models.DAL.DocTagsDAL;
import java.util.List;

public class DocTagsDTO {
    public boolean success;
    public String message;
    public List<DocTagsDAL> list;

    public DocTagsDTO(boolean Success, String Message, List<DocTagsDAL> List){
        success = Success;
        message = Message;
        list = List;
    }
}
