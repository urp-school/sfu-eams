/*
 * 创建日期 2005-8-28
 *
 * 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */

package com.shufe.web;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.dom.DOMDocumentType;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 
 * @author dell
 */
public class MergeStrutsConfig {
    
    public static void main(String[] args) {
        SAXReader saxReader = new SAXReader();
        String realPath = "src/main/webapp/WEB-INF/strutsConf/";
        
        Document document = DocumentHelper.createDocument();
        DOMDocumentType doctype = new DOMDocumentType(
                "struts-config",
                "-//Apache Software Foundation//DTD Struts Configuration 1.2//EN\" \"http://struts.apache.org/dtds/struts-config_1_1.dtd",
                "");
        document.setDocType(doctype);
        Element rootElement = document.addElement("struts-config");
        Element formBeansElement = rootElement.addElement("form-beans");
        
        try {
            Document globeConf = saxReader.read(new File(realPath + "global-config.xml"));
            Node global_exceptions = (Node) globeConf.selectSingleNode("//global-exceptions")
                    .clone();
            rootElement.add(global_exceptions);
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }
        
        try {
            Document globeConf = saxReader.read(new File(realPath + "global-config.xml"));
            Node global_forwards = (Node) globeConf.selectSingleNode("//global-forwards").clone();
            rootElement.add(global_forwards);
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }
        
        Element actionMappingElement = rootElement.addElement("action-mappings");
        
        try {
            Document globeConf = saxReader.read(new File(realPath + "global-config.xml"));
            Node controller = (Node) globeConf.selectSingleNode("//controller").clone();
            rootElement.add(controller);
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }
        
        try {
            Document globeConf = saxReader.read(new File(realPath + "global-config.xml"));
            List resourcesList = globeConf.selectNodes("//message-resources");
            for (Iterator ite = resourcesList.iterator(); ite.hasNext();) {
                Node message_resources = (Node) ((Node) ite.next()).clone();
                rootElement.add(message_resources);
            }
            // Node message_resources = (Node)
            // globeConf.selectSingleNode("//message-resources").clone();
            // rootElement.add(message_resources);
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }
        
        try {
            Document globeConf = saxReader.read(new File(realPath + "global-config.xml"));
            Node plug_in = (Node) globeConf.selectSingleNode("//plug-in").clone();
            rootElement.add(plug_in);
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }
        
        try {
            File path = new File(realPath);
            if (path.isDirectory()) {
                String[] fileList = path.list();
                List form_beans_temp = new ArrayList();
                List action_mapping_temp = new ArrayList();
                
                for (int i = 0; i < fileList.length; i++) {
                    String filePath = realPath + "/" + fileList[i];
                    
                    File f = new File(filePath);
                    if (f.isFile()) {
                        Document document_temp = saxReader.read(f);
                        form_beans_temp.addAll(document_temp.selectNodes("//form-bean"));
                        action_mapping_temp.addAll(document_temp.selectNodes("//action"));
                    }
                }
                formBeansElement.setContent(form_beans_temp);
                actionMappingElement.setContent(action_mapping_temp);
            }
            OutputFormat format = OutputFormat.createPrettyPrint();
            
            XMLWriter output = new XMLWriter(new FileWriter(new File(
                    "src/main/webapp/WEB-INF/struts-config.xml")), format);
            output.write(document);
            output.close();
            
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Generate Successful!");
    }
}
