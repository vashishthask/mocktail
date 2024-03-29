package in.malonus.mocktail.metadata.aspect.playback;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.DirectFieldAccessor;

import in.malonus.mocktail.metadata.MocktailContainer;

public class PlaybackAspectTest {

    DirectFieldAccessor dfa;
    private PlaybackAspect playbackAspect;

    @Before
    public void setup() {
        dfa = new DirectFieldAccessor(MocktailContainer.getInstance());
        dfa.setPropertyValue("recordingDirectory", "src/test/resources/recording");
    }

    @Test
    @Ignore
    public void testPlaybackForRecordings() throws Exception {
        playbackAspect = new PlaybackAspect();
        playbackAspect.fqcn = "in.malonus.mocktail.metadata.aspect.recorder";
        Object recordedObject = playbackAspect.playback("sandy", "ganesh", 12, 23.0);
        assertNotNull(recordedObject);
        assertTrue(recordedObject instanceof String);
        assertEquals("to be recorded object", (String) recordedObject);

    }

    @Test(expected = java.lang.AssertionError.class)
    public void testPlaybackForInvalidRecordingDir() throws Exception {
        dfa.setPropertyValue("recordingDirectory", "src/test/resources/non_existent_recording_dir");
        playbackAspect = new PlaybackAspect();
        playbackAspect.fqcn = "in.malonus.mocktail.metadata.aspect.recorder";
        Object recordedObject = playbackAspect.playback("sandy", "ganesh", 12, 23.0);
        assertNull(recordedObject);
    }

    @Test(expected = java.lang.AssertionError.class)
    public void testPlaybackForInvalideFQCN() throws Exception {
        dfa.setPropertyValue("recordingDirectory", "src/test/resources/recording");
        playbackAspect = new PlaybackAspect();
        playbackAspect.fqcn = "invalid_fqcn";
        Object recordedObject = playbackAspect.playback("sandy", "ganesh", 12, 23.0);
        assertNull(recordedObject);
    }

    @Test(expected = java.lang.AssertionError.class)
    public void testPlaybackForRecordingNotAvailable() throws Exception {
        playbackAspect = new PlaybackAspect();
        playbackAspect.fqcn = "in.malonus.mocktail.metadata.aspect.recorder";
        Object recordedObject = playbackAspect.playback("sandh", "ganesh", 12, 23.0);
        assertNull(recordedObject);
    }

}
