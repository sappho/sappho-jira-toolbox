package uk.org.sappho.jira.plugin.toolbox.assyst;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class OutputTest {

    @Test
    public void testOutput() throws Exception {
        test("action");
        test("event");
    }

    public void test(String type) throws Exception {
        String expectedFilename = "TEST-7890-" + type + "-19700101010000.imp";
        Output output = new Output(new Date(0), "I4321", "TEST-7890", "http://jira.example.com", "localhost", "jira");
        String filename = output.getFilename(type);
        assertTrue(filename.equals("\\\\localhost\\jira\\" + expectedFilename));
        output.write(expectedFilename, type);
        InputStream ourVersion = new FileInputStream(expectedFilename);
        InputStream correctVersion =
                new FileInputStream("src/test/resources/uk/org/sappho/jira/plugin/toolbox/assyst/" + expectedFilename);
        int data;
        while ((data = correctVersion.read()) != -1) {
            assertTrue(data == ourVersion.read());
        }
        assertTrue(ourVersion.read() == -1);
        ourVersion.close();
        assertTrue(new File(expectedFilename).delete());
    }
}
