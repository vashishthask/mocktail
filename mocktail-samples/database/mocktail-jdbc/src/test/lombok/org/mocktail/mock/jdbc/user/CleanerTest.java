package in.malonus.mocktail.metadata.mock.jdbc.user;

import lombok.Cleanup;

import org.junit.Test;

public class CleanerTest {
    @Test
    public void testCleaner() throws Exception {
        @lombok.Cleanup CleanupClient cleanupClient = new CleanupClient();
        cleanupClient.setup();
    }
    
    private class CleanupClient {
        public void setup(){
            System.out.println("Inside setup");
        }
        
        public void close(){
            System.out.println("Inside close");
        }
    }

}
