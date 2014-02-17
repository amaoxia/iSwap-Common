package com.common.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.common.config.Dictionary.Category;

/**  
 * 配置文件读取
 *@author hudaowan
 *@version  iSwap V5.0
 *@date  Jul 22, 2008 9:13:07 AM
 *@Team 数据交换平台研发小组
 */
public class ConfigAccess {
    //public static final String CONTEXT_PATH = "com.common.config";
    /** 配置文件名称 */
    //public static final String CONF_FILE = "/conf/config.xml";
      
    public static final String CONTEXT_PATH = "com.common.config";
    public static final String CONF_FILE = "/config/config.xml";
    private Map<String, String> props = new HashMap<String, String>();
    private Map<String, Category> categories = new HashMap<String, Category>();
    private Map<String, Entry> entry = new HashMap<String, Entry>();
    private static ConfigAccess instance;
    private ConfigAccess() {
    }
    public synchronized static ConfigAccess init() {
        if (instance == null) {
            instance = new ConfigAccess();
            try {
                instance.load(CONTEXT_PATH, CONF_FILE);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Config load error!", e);
            }
        }
        return instance;
    }
    
    /**
     * 根据key等到属性
     *@author hudaowan
     *@date  Jul 22, 2008 9:26:01 AM
     *@param key
     *@return
     */
    public String findProp(String key) {
        return props.get(key);
    }
    
    /**
     *通过enname等到一个list
     *@author hudaowan
     *@date  Jul 22, 2008 9:26:33 AM
     *@param enName
     *@return
     */
    public Category findCategory(String enName) {
        return categories.get(enName);
    }

    public Entry findEntry(String categoryEnName, String entryCode) {
        return entry.get(categoryEnName + "_" + entryCode);
    }
    
    public String findEntryName(String categoryEnName, String entryCode){
        return entry.get(categoryEnName + "_" + entryCode).getName();
    }
    private void load(String contextPath, String fileClassPath)
            throws JAXBException, SAXException, ParserConfigurationException,
            IOException {
        JAXBContext context = JAXBContext.newInstance(contextPath);

        final Configuration.Listener propListener = new Configuration.Listener() {
            public void handleProp(Configuration conf, Prop o) {
                if (o.getKey() != null)
                    props.put(o.getKey(), o.getValue());
            }
        };

        final Dictionary.Listener cateListener = new Dictionary.Listener() {
            public void handleCategory(Dictionary dict, Category o) {
                if (o.getEnName() != null)
                    categories.put(o.getEnName(), o);
            }
        };

        final Category.Listener entryListener = new Category.Listener() {
            public void handleEntry(Category cate, Entry o) {
                if (o.getCode() != null)
                    entry.put(cate.getEnName() + "_" + o.getCode(), o);
            }
        };

        Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setListener(new Unmarshaller.Listener() {
            public void beforeUnmarshal(Object target, Object parent) {
                if (target instanceof Configuration) {
                    ((Configuration) target).setPropListener(propListener);
                } else if (target instanceof Dictionary) {
                    ((Dictionary) target).setCategoryListener(cateListener);
                } else if (target instanceof Category) {
                    ((Category) target).setEntryListener(entryListener);
                }
            }

            @Override
            public void afterUnmarshal(Object target, Object parent) {
                if (target instanceof Configuration) {
                    ((Configuration) target).setPropListener(null);
                } else if (target instanceof Dictionary) {
                    ((Dictionary) target).setCategoryListener(null);
                }
            }

        });

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XMLReader reader = factory.newSAXParser().getXMLReader();
        reader.setContentHandler(unmarshaller.getUnmarshallerHandler());
        reader.parse(new InputSource(this.getClass().getResourceAsStream(fileClassPath)));
    }


    public void reload(String contextPath, String pathname) {
        props.clear();
        categories.clear();
        try {
            load(contextPath, pathname);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Config load error!", e);
        }
    }

    

 public static void main(String[] args) throws Exception {
      ConfigAccess config = new ConfigAccess();
      config.load(CONTEXT_PATH, CONF_FILE);
  }

}
