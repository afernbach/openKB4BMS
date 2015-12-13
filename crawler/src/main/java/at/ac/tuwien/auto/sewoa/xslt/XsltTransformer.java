package at.ac.tuwien.auto.sewoa.xslt;

import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;

/**
 * User: pelesic
 * Date: 23.06.15
 * Time: 12:44
 */
public class XsltTransformer {

    static final Logger LOGGER = LoggerFactory.getLogger(XsltTransformer.class);

    private Source xsltSource;

    public XsltTransformer(String xsltFile) {
        try {
            String xslt = Resources.toString(this.getClass().getResource("/"+xsltFile), Charset.defaultCharset());
            this.xsltSource  = new StreamSource(new StringReader(xslt));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String convert(String payload) throws RuntimeException  {

        StringWriter stringWriter = null;
        try {
            stringWriter = _convert(payload);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

        return stringWriter.toString();
    }

    private StringWriter _convert(String payload) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        javax.xml.transform.Transformer transformer = transformerFactory.newTransformer(this.xsltSource);
        StringWriter stringWriter = new StringWriter();

        Source dataSource =  new StreamSource(new StringReader(payload));

        transformer.transform(dataSource, new StreamResult(stringWriter));
        return stringWriter;
    }


}
