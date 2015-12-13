package at.ac.tuwien.auto.sewoa.obix;

import at.ac.tuwien.auto.sewoa.xslt.XsltTransformer;

/**
 * Created by karinigor on 06.12.2015.
 */
public class ObixModelConverter {

    private String restURL;
    private String xsltFile;

    private ObixRetriever obixRetriever;
    private XsltTransformer xsltTransformer;

    public ObixModelConverter(String restUrl, String xsltFile){
       obixRetriever =  new ObixRetriever(restUrl);
       xsltTransformer =  new XsltTransformer(xsltFile);
    }

    public String fetchAndConvert(){
        String obixXml = obixRetriever.retrieveObix();
        String convert = xsltTransformer.convert(obixXml);
        return convert;
    }







}
