 /**
     * CS180 PJ 4
     *
     * Message class
     *
     * @author Yifei Wang
     * @version 4/5/2023
     */


public class Message {
   
    private String messsage;
    private User sender;
    private User reciever;
    private String timestamp;
    private boolean edited;
    //  method that creates conversation file if need be, then adds message to conversation text file
    public boolean updateConversation(Message mes) {
        this.messsage = mes.messsage;
        this.sender = mes.sender;
        this.reciever = mes.reciever;
        this.timestamp = mes.timestamp;
        this.edited = mes.edited;
        return true;
        //return false;
    }

    //  need constructor, gettters and setters for all above variables, implement specific methods marked below

    public Message(String messsage, User sender, User reciever, String timestamp) {
        this.messsage = messsage;
        this.sender = sender;
        this.reciever = reciever;
        this.timestamp = timestamp;
        this.edited = false;
    }



    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
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

    @Override
    public String toString() {
        return messsage + "," + sender.getName() + "," + timestamp + "," + edited;
    }

}
