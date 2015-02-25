package com.backbase.exercises.ptc.dataproviders;

import com.backbase.portal.ptc.config.element.DataProviderConfig;
import com.backbase.portal.ptc.context.MutableProxyContext;
import com.backbase.portal.ptc.provider.DataProvider;
import com.backbase.portal.ptc.request.ProxyRequest;
import com.backbase.portal.ptc.response.MutableProxyResponse;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CMISDataProvider implements DataProvider {

    private static final Logger log = LoggerFactory.getLogger(CMISDataProvider.class);
    private static final String TEXT_XML = "text/xml";
    private static final String UTF8_ENCODING = "UTF-8";
    private static final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

    private static Session session;

    static {
        documentBuilderFactory.setNamespaceAware(true);
    }

    public MutableProxyResponse executeRequest(DataProviderConfig config, MutableProxyContext ctx, ProxyRequest proxyRequest) {
        MutableProxyResponse mutableProxyResponse = new MutableProxyResponse();
        mutableProxyResponse.addContentTypeHeader(TEXT_XML, UTF8_ENCODING);
        try {
            byte[] content = getContent(createContentSession(config), config.getParamValue("documentID"));
            mutableProxyResponse.setBodyBytes(content);
            mutableProxyResponse.setStatusCode(200);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred retrieving data from the content repository");
        }
        return mutableProxyResponse;
    }

    private static byte[] getContent(Session session, String docId) throws IOException {
        CmisObject object = session.getObject(session.createObjectId(docId));
        Document document = (Document) object;
        log.debug("CS Filename: "+document.getName());
        InputStream stream = document.getContentStream().getStream();
        return IOUtils.toByteArray(stream);
    }

    private static synchronized Session createContentSession(DataProviderConfig config) throws ParserConfigurationException, SAXException, IOException {
        if (session == null) {
            log.debug("Creating CS session....");
            SessionFactory factory = SessionFactoryImpl.newInstance();
            Map<String, String> parameter = new HashMap<String, String>();
            parameter.put(SessionParameter.USER, config.getParamValue("user"));
            parameter.put(SessionParameter.PASSWORD, config.getParamValue("password"));
            parameter.put(SessionParameter.ATOMPUB_URL, config.getParamValue("repositoryURL"));
            parameter.put(SessionParameter.REPOSITORY_ID, config.getParamValue("repository"));
            parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
            session = factory.createSession(parameter);
            log.debug(".....Done creating CS session");
        }
        return session;
    }
}