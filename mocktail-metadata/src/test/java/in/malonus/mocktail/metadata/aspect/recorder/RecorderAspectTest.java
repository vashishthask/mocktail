package in.malonus.mocktail.metadata.aspect.recorder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.DirectFieldAccessor;

import in.malonus.mocktail.metadata.MocktailContainer;

public class RecorderAspectTest {

    DirectFieldAccessor dfa;
    private RecorderAspect recorderAspect;

    @Before
    public void setup() {
        MocktailContainer.getInstance().clean();
        dfa = new DirectFieldAccessor(MocktailContainer.getInstance());
    }

    @Test(expected = AssertionError.class)
    public void shouldNotifyRootRecordingDirectoryNotAvailable()
            throws Exception {
        dfa.setPropertyValue("recordingDirectory",
                "src/test/resources/non_existent_recording_dir");
        recorderAspect = new RecorderAspect();
        recorderAspect.fqcn = "in.malonus.mocktail.metadata.aspect.recorder";
        recorderAspect.doTheRecording("to be recorded object", "sandy",
                "ganesh", 12, 23.0);

    }

    @Test
    @Ignore
    //FIXME
    public void shouldCreateRecordingDirectory() throws Exception {
        String rootRecordingDirectory = "src/test/resources/recording";
        String packagePath = "in.malonus.mocktail.metadata.aspect.recorder.new";
        File recordingDir = new File(rootRecordingDirectory + File.separator
                + packagePath.replace(".", File.separator));
        
        System.out.println(recordingDir.delete());
        
        System.out.println(recordingDir.getPath());

        assertFalse("Recording directory already exists", recordingDir.exists());

        dfa.setPropertyValue("recordingDirectory", rootRecordingDirectory);
        recorderAspect = new RecorderAspect();
        recorderAspect.fqcn = packagePath;
        recorderAspect.doTheRecording("to be recorded object", "sandy",
                "ganesh", 12, 23.0);

        assertTrue("Recording directory doesn't exists", recordingDir.exists());
        (new File(recordingDir, "1359766052")).delete();
        recordingDir.delete();
    }

    // Should not do the recording if recording already exists
    @Test
    public void shouldExecuteIfRecordingExists() throws Exception {
        String rootRecordingDirectory = "src/test/resources/recording";
        String packagePath = "in.malonus.mocktail.metadata.aspect.recorder";
        File recordingFile = new File(rootRecordingDirectory + File.separator
                + packagePath.replace(".", File.separator)
                + File.separator + "1359766052");

        assertTrue("Recording File doesn't exists", recordingFile.exists());

        dfa.setPropertyValue("recordingDirectory", rootRecordingDirectory);
        recorderAspect = new RecorderAspect();
        recorderAspect.fqcn = packagePath;
        recorderAspect.doTheRecording("to be recorded object", "sandy",
                "ganesh", 12, 23.0);
    }

    @Test
    @Ignore
    //FIXME
    public void shouldDoRecordingIfRecordingNotTheir() throws Exception {
        String rootRecordingDirectory = "src/test/resources/recording";
        String packagePath = "in.malonus.mocktail.metadata.aspect.recorder.new";
        File recordingFile = new File(rootRecordingDirectory + File.separator
                + packagePath.replace(".", File.separator)
                + File.separator + "1359766052");

        assertFalse("Recording File already exists", recordingFile.exists());

        dfa.setPropertyValue("recordingDirectory", rootRecordingDirectory);
        recorderAspect = new RecorderAspect();
        recorderAspect.fqcn = packagePath;
        recorderAspect.doTheRecording("to be recorded object", "sandy",
                "ganesh", 12, 23.0);

        assertTrue("Recording File doesn't exists", recordingFile.exists());
        recordingFile.delete();
    }
}
