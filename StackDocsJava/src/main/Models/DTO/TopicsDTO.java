package Models.DTO;

import Models.DAL.TopicsDAL;

import java.util.List;

public class TopicsDTO {
    public boolean success;
    public String message;
    public List<TopicsDAL> list;

    public TopicsDTO(boolean Success, String Message, List<TopicsDAL> List){
        success = Success;
        message = Message;
        list = List;
    }
}
