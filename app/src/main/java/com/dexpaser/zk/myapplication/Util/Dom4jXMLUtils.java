package com.dexpaser.zk.myapplication.Util;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by zk on 2017/8/25.
 */

public class Dom4jXMLUtils {

    public static Document getDocument(String path)
    {
        try
        {
            SAXReader reader = new SAXReader();
            FileInputStream in = new FileInputStream(path);
            return reader.read(in);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Document getDocumentFromBuf(String buf)
    {
        try
        {
            System.out.println(buf);
            boolean bret = buf.contains("xmlns:axml_auto_00=\"\"");
            if (bret)
            {
                int index = buf.indexOf("xmlns:axml_auto_00=\"\"");
                String right = buf.substring(index + "xmlns:axml_auto_00=\"\"".length(), buf.length());
                String center = " xmlns:axml_auto_00=\"hello\"";
                String left = buf.substring(0, index - 2);
                buf = left + center + right;
            }
            SAXReader reader = new SAXReader();
            InputStream in = new ByteArrayInputStream(buf.getBytes("utf-8"));
            return reader.read(in);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Element getElement(Document doc, String name)
    {
        try
        {
            Element root = doc.getRootElement();
            if (root.getName().equals(name)) {
                return root;
            }
            return getChildElement(root, name);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private static Element getChildElement(Element root, String name)
    {
        try
        {
            List<Element> childNodes = root.elements();
            for (Element e : childNodes) {
                if (e.getName().equals(name)) {
                    return e;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private static Element getChildElement2(Element root, String name)
    {
        try
        {
            List<Element> childNodes = root.elements();
            for (Element e : childNodes) {
                if (e.getName().equals(name))
                {
                    Element intent = getChildElement(e, "intent-filter");
                    if (intent != null)
                    {
                        Element action = getChildElement(intent, "action");
                        if (action != null)
                        {

                            Attribute actionName = find(action, "name");
                            if (actionName != null) {

                                if ((actionName.getValue().equals("android.intent.action.MAIN"))
                                    //&&  (categoryName.getValue().equals("android.intent.category.LAUNCHER"))
                                        ) {
                                    return e;
                                }

                            }


                          /*  Element category = getChildElement(intent, "category");
                            if (category != null)
                            {
                                Attribute actionName = find(action, "name");
                                if (actionName != null)
                                {
                                    Attribute categoryName = find(category, "name");
                                    if (categoryName != null) {
                                        if ((actionName.getValue().equals("android.intent.action.MAIN"))
                                                //&&  (categoryName.getValue().equals("android.intent.category.LAUNCHER"))
                                                ) {
                                            return e;
                                        }
                                    }
                                }
                            }*/
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Attribute find(Element element, String name)
    {
        List<Attribute> attrs = element.attributes();
        if ((attrs != null) && (attrs.size() > 0)) {
            for (Attribute attr : attrs) {
                if (attr.getName().equals(name)) {
                    return attr;
                }
            }
        }
        return null;
    }

    public static String getPackageName(String path)
    {
        try
        {
            Document doc = getDocumentFromBuf(path);

            Element element = null;
            if (doc != null) {
                element = getElement(doc, "manifest");
            }
            if (element != null)
            {
                Attribute attr = find(element, "package");
                if (attr != null) {
                    return attr.getValue();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMainActivty(String xml)
    {
        try
        {
            Document doc = getDocumentFromBuf(xml);

            Element element = null;
            if (doc != null) {
                element = getElement(doc, "application");
            }
            if (element != null)
            {
                Element act = getChildElement2(element, "activity");
                if (act == null) {
                    return null;
                }
                Attribute attr = find(act, "name");
                if (attr != null) {
                    return attr.getValue();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String getApplicationName(String xml)
    {
        try
        {
            Document doc = getDocumentFromBuf(xml);

            Element element = null;
            if (doc != null) {
                element = getElement(doc, "application");
            }
            if (element != null)
            {
                Attribute attr = find(element, "name");
                if (attr != null) {
                    return attr.getValue();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
