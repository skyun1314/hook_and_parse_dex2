package com.dexpaser.zk.myapplication.Util;

import android.content.res.free.AXmlResourceParser;

import java.io.File;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by zk on 2017/8/25.
 */

public class AXMLPrinter {


    private static final String DEFAULT_XML = "AndroidManifest.xml";


    public static String getManifestXMLFromAPK(String apkPath)
    {
        ZipFile file = null;
        StringBuilder xmlSb = new StringBuilder(100);
        try
        {
            File apkFile = new File(apkPath);
            file = new ZipFile(apkFile, 1);
            ZipEntry entry = file.getEntry("AndroidManifest.xml");

            AXmlResourceParser parser = new AXmlResourceParser();
            parser.open(file.getInputStream(entry));

            StringBuilder sb = new StringBuilder(10);
            String indentStep = "\t";
            int type;
            while ((type = parser.next()) != 1) {
                switch (type)
                {
                    case 0:
                        log(xmlSb, "<?xml version=\"1.0\" encoding=\"utf-8\"?>", new Object[0]);
                        break;
                    case 2:
                        log(false, xmlSb, "%s<%s%s", new Object[] { sb,
                                getNamespacePrefix(parser.getPrefix()), parser.getName() });
                        sb.append("\t");

                        int namespaceCountBefore = parser.getNamespaceCount(parser.getDepth() - 1);
                        int namespaceCount = parser.getNamespaceCount(parser.getDepth());
                        for (int i = namespaceCountBefore; i != namespaceCount; i++) {
                            log(xmlSb, "%sxmlns:%s=\"%s\"", new Object[] { i == namespaceCountBefore ? "  " : sb, parser

                                    .getNamespacePrefix(i), parser
                                    .getNamespaceUri(i) });
                        }
                        int i = 0;
                        for (int size = parser.getAttributeCount(); i != size; i++) {
                            log(false, xmlSb, "%s%s%s=\"%s\"", new Object[] { " ",
                                    getNamespacePrefix(parser.getAttributePrefix(i)), parser
                                    .getAttributeName(i),
                                    getAttributeValue(parser, i) });
                        }
                        log(xmlSb, ">", new Object[0]);
                        break;
                    case 3:
                        sb.setLength(sb.length() - "\t".length());
                        log(xmlSb, "%s</%s%s>", new Object[] { sb,
                                getNamespacePrefix(parser.getPrefix()), parser
                                .getName() });
                        break;
                    case 4:
                        log(xmlSb, "%s%s", new Object[] { sb, parser.getText() });
                }
            }
            parser.close();
            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return xmlSb.toString();
    }

    private static String getNamespacePrefix(String prefix)
    {
        if ((prefix == null) || (prefix.length() == 0)) {
            return "";
        }
        return prefix + ":";
    }

    private static String getAttributeValue(AXmlResourceParser parser, int index)
    {
        int type = parser.getAttributeValueType(index);
        int data = parser.getAttributeValueData(index);
        if (type == 3) {
            return parser.getAttributeValue(index);
        }
        if (type == 2) {
            return String.format("?%s%08X", new Object[] { getPackage(data), Integer.valueOf(data) });
        }
        if (type == 1) {
            return String.format("@%s%08X", new Object[] { getPackage(data), Integer.valueOf(data) });
        }
        if (type == 4) {
            return String.valueOf(Float.intBitsToFloat(data));
        }
        if (type == 17) {
            return String.format("0x%08X", new Object[] { Integer.valueOf(data) });
        }
        if (type == 18) {
            return data != 0 ? "true" : "false";
        }
        if (type == 5) {
            return Float.toString(complexToFloat(data)) + DIMENSION_UNITS[(data & 0xF)];
        }
        if (type == 6) {
            return Float.toString(complexToFloat(data)) + FRACTION_UNITS[(data & 0xF)];
        }
        if ((type >= 28) && (type <= 31)) {
            return String.format("#%08X", new Object[] { Integer.valueOf(data) });
        }
        if ((type >= 16) && (type <= 31)) {
            return String.valueOf(data);
        }
        return String.format("<0x%X, type 0x%02X>", new Object[] { Integer.valueOf(data), Integer.valueOf(type) });
    }

    private static String getPackage(int id)
    {
        if (id >>> 24 == 1) {
            return "android:";
        }
        return "";
    }

    private static void log(StringBuilder xmlSb, String format, Object... arguments)
    {
        log(true, xmlSb, format, arguments);
    }

    private static void log(boolean newLine, StringBuilder xmlSb, String format, Object... arguments)
    {
        xmlSb.append(String.format(format, arguments));
        if (newLine) {
            xmlSb.append("\n");
        }
    }

    public static float complexToFloat(int complex)
    {
        return (complex & 0xFF00) * RADIX_MULTS[(complex >> 4 & 0x3)];
    }

    private static final float[] RADIX_MULTS = { 0.00390625F, 3.051758E-5F, 1.192093E-7F, 4.656613E-10F };
    private static final String[] DIMENSION_UNITS = { "px", "dip", "sp", "pt", "in", "mm", "", "" };
    private static final String[] FRACTION_UNITS = { "%", "%p", "", "", "", "", "", "" };
}
