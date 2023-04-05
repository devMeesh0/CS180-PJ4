
public class Message {

    private String messsage;
    private User sender;
    private User reciever;
    private String timestamp;
    //TODO: method that creates conversation file if need be, then adds message to conversation text file
    public boolean updateConversation(Message mes) {
        this.messsage = mes.messsage;
        this.sender = mes.sender;
        this.reciever = mes.reciever;
        this.timestamp = mes.timestamp;
        return true;
//        return false;
    }

    //TODO: need constructor, gettters and setters for all above variables, implement specific methods marked below

    public Message(String messsage, User sender, User reciever, String timestamp) {
        this.messsage = messsage;
        this.sender = sender;
        this.reciever = reciever;
        this.timestamp = timestamp;
    }





    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReciever() {
        return reciever;
    }

    public void setReciever(User reciever) {
        this.reciever = reciever;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
