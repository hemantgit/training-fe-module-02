/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backbase.exercises.utils;

import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisRuntimeException;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zeljko
 */
public class CmisUtil {

    public static Session getAtomSession(String username, String password, String url, String repository) {
        // default factory implementation
        SessionFactory factory = SessionFactoryImpl.newInstance();
        Map<String, String> parameter = new HashMap<String, String>();

        // user credentials
        parameter.put(SessionParameter.USER, username);
        parameter.put(SessionParameter.PASSWORD, password);

        // connection settings
        parameter.put(SessionParameter.ATOMPUB_URL, url);
        parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
        parameter.put(SessionParameter.REPOSITORY_ID, repository);

        // create session
        return factory.createSession(parameter);
    }

    public static synchronized Document createNewDocument(Session session, String folderPath, String fileName, String strContent, String mimetype, Map<String, Object> properties) {
        Folder folder = (Folder) session.getObjectByPath(folderPath);

        Map<String, Object> _properties = new HashMap<String, Object>();
        _properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
        _properties.put(PropertyIds.NAME, fileName);
        if (properties != null) {
            _properties.putAll(properties);
        }
        byte[] content = strContent.getBytes();
        InputStream stream = new ByteArrayInputStream(content);
        ContentStream contentStream = new ContentStreamImpl(fileName, BigInteger.valueOf(content.length), mimetype, stream);
        return folder.createDocument(_properties, contentStream, VersioningState.MAJOR);
    }

    public static synchronized Document updateDocumentContent(Session session, String path, String strContent) {
        CmisObject object = session.getObjectByPath(path);
        if (object instanceof Document) {
            Document document = (Document) object;
            byte[] buf = null;
            try {
                buf = strContent.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            InputStream input = new ByteArrayInputStream(buf);
            ContentStream contentStream = session.getObjectFactory().createContentStream(document.getContentStreamFileName(), buf.length, document.getContentStreamMimeType(), input);
            document.setContentStream(contentStream, true);

            return (Document) object;
        }

        return null;
    }

    public static synchronized boolean documentExist(Session session, String path) {
        try {
            CmisObject object = session.getObjectByPath(path);
            return object != null;
        } catch (CmisObjectNotFoundException e) {
        } catch (CmisRuntimeException e) {
        } catch (Throwable e) {
        }
        return false;
    }

    public static void deleteDocument(Session session, String path) {
        session.getObjectByPath(path).delete(true);
    }
}
