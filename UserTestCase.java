import org.junit.Test;
import static org.junit.Assert.*;
import java.io.*;
import java.util.*;
public class UserTestCase {
    @Test
    public void testGetMessageHistoryByReciever() {
        
        User user1 = new User("user1", "password","alex",6,1234567890,"abcdefg");
        User user2 = new User("user2", "password","boby",4,1234568901,"unugjj");
        User user3 = new User("user3", "password","cat",4,1237898989,"sfadf");
        List<Message> messageHistory = new ArrayList<>();
        Message message1 = new Message("I love you", user1, user3,"03:19");
        Message message2 = new Message("Oh my god!", user3,user1,"03:20");
        Message message3 = new Message("I hate you", user2,user1, "03:21");
        messageHistory.add(message1);
        messageHistory.add(message2);
        messageHistory.add(message3);
    
        List<Message> result = user1.getMessageHistoryByReciever(null);
        assertEquals(new ArrayList<>(), result);
        // TODO：I really don't know how to write User's Test Case.
        
    }

    
    
    
    @Test
    public void testGetMessageHistoryBySender() {
        //TODO
    }
    @Test
    public void testModifyUser() {
        User user1 = new User("Alex", "12345678", "JS", 3, 12347585, "add1");
        
    }
   
    @Test
    public void testDeleteUser() {
        //TODO
    }
    @Test
    public void testBlockUser() {
        //TODO
    }
    @Test
    public void testMakeInvisible() {
        //TODO
    }
    
}
