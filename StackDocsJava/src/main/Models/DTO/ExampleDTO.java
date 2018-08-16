package Models.DTO;

import Models.DAL.ExamplesDAL;

import java.util.List;

public class ExampleDTO {

    public boolean success;
    public String message;
    public List<ExamplesDAL> list;

    public ExampleDTO(boolean Success, String Message, List<ExamplesDAL> List) {
        success = Success;
        message = Message;
        list = List;
    }
}
