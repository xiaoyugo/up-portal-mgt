package com.changhong.base.utils.bean;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.util.Assert;

/**
 * copy from springside中的JaxbMapper
 * 
 * 使用Jaxb2.0实现XML<->Java Object的Mapper.
 * 
 * 在创建时需要设定所有需要序列化的Root对象的Class.
 * 特别支持Root对象是Collection的情形.
 */
public class JaxbMapper {

	//private final static Log log = LogFactory.getLog(JaxbMapper.class);
	@SuppressWarnings("unchecked")
	private static ConcurrentMap<Class, JAXBContext> jaxbContexts = new ConcurrentHashMap<Class, JAXBContext>();

	/**
	 * Java Object->Xml without encoding.
	 */
	@SuppressWarnings("unchecked")
	public static String toXml(Object root) {
		Class clazz = ReflectionUtils.getClass(root);
		return toXml(root, clazz, null);
	}

	/**
	 * Java Object->Xml with encoding.
	 */
	@SuppressWarnings("unchecked")
	public static String toXml(Object root, String encoding) {
		Class clazz = ReflectionUtils.getClass(root);
		return toXml(root, clazz, encoding);
	}

	/**
	 * Java Object->Xml with encoding.
	 */
	@SuppressWarnings("unchecked")
	public static String toXml(Object root, Class clazz, String encoding) {
		String xml = "";
		try {
			StringWriter writer = new StringWriter();
			createMarshaller(clazz, encoding).marshal(root, writer);
			xml = writer.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return xml;
	}

	/**
	 * Java Collection->Xml without encoding, 特别支持Root Element是Collection的情形.
	 */
	@SuppressWarnings("unchecked")
	public static String toXml(Collection<?> root, String rootName, Class clazz) {
		return toXml(root, rootName, clazz, null);
	}

	/**
	 * Java Collection->Xml with encoding, 特别支持Root Element是Collection的情形.
	 */
	@SuppressWarnings("unchecked")
	public static String toXml(Collection<?> root, String rootName, Class clazz, String encoding) {
		String xml = "";
		try {
			CollectionWrapper wrapper = new CollectionWrapper();
			wrapper.collection = root;

			JAXBElement<CollectionWrapper> wrapperElement = new JAXBElement<CollectionWrapper>(new QName(rootName),
					CollectionWrapper.class, wrapper);

			StringWriter writer = new StringWriter();
			createMarshaller(clazz, encoding).marshal(wrapperElement, writer);

			xml = writer.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return xml;
	}

	/**
	 * Xml->Java Object.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromXml(String xml, Class<T> clazz) {
		try {
			StringReader reader = new StringReader(xml);
			return (T) createUnmarshaller(clazz).unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 创建Marshaller并设定encoding(可为null).
	 * 线程不安全，需要每次创建或pooling。
	 */
	@SuppressWarnings("unchecked")
	public static Marshaller createMarshaller(Class clazz, String encoding) {
		try {
			JAXBContext jaxbContext = getJaxbContext(clazz);

			Marshaller marshaller = jaxbContext.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			if (StringUtils.isNotBlank(encoding)) {
				marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			}

			return marshaller;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 创建UnMarshaller.
	 * 线程不安全，需要每次创建或pooling。
	 */
	@SuppressWarnings("unchecked")
	public static Unmarshaller createUnmarshaller(Class clazz) {
		try {
			JAXBContext jaxbContext = getJaxbContext(clazz);
			return jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected static JAXBContext getJaxbContext(Class clazz) {
		Assert.notNull(clazz, "'clazz' must not be null");
		JAXBContext jaxbContext = jaxbContexts.get(clazz);
		if (jaxbContext == null) {
			try {
				jaxbContext = JAXBContext.newInstance(clazz, CollectionWrapper.class);
				jaxbContexts.putIfAbsent(clazz, jaxbContext);
			} catch (JAXBException ex) {
				throw new HttpMessageConversionException("Could not instantiate JAXBContext for class [" + clazz
						+ "]: " + ex.getMessage(), ex);
			}
		}
		return jaxbContext;
	}

	/**
	 * 封装Root Element 是 Collection的情况.
	 */
	public static class CollectionWrapper {
		@XmlAnyElement
		protected Collection<?> collection;
	}
	
	public static void main(String[] args) throws IOException{
		
	   /* QueryMapping m = new QueryMapping();
	    QueryMapping.Query q = new QueryMapping.Query();
	    q.setName("111");
	    q.setHql("2222");
	    q.setSql("333333333");
	    List<QueryMapping.Query> l = new ArrayList<QueryMapping.Query>();
	    l.add(q);
	    m.setQuery(l);
	   
	    System.out.println(toXml(m));
	   
	    //System.out.println(toXml(b));
	    
	    String s = FileUtils.readFileToString(new File("D:/system.query.xml"), "UTF-8");
	    System.out.println("aaaa:"+s);
	    QueryMapping daa = fromXml(s,QueryMapping.class);
	    System.out.println(daa.toString());*/
	}

}
