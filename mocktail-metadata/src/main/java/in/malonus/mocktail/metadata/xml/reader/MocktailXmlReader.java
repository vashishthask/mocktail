package in.malonus.mocktail.metadata.xml.reader;

import java.io.InputStream;
import java.util.List;

import in.malonus.mocktail.metadata.xml.domain.Mocktail;

public interface MocktailXmlReader {

    List<Mocktail> readXml(InputStream sampleXmlStream);

}
