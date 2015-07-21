package factoryBean;

import static org.springframework.util.Assert.notNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.StringUtils;

/**
 * such as:
 * <pre class="code">
 * {@code
 * <bean id="mapperBeanFactory" class="xxx.MapperBeanFactory">
 *    <property name="basePackage" value="xxx.dao" />
 *    <property name="sqlSessionFactorys">
 *      <map key-type="java.lang.String">
 *        <entry key="crm1" value-ref="sqlSessionFactoryCrm1"/>
 *        <entry key="crm2" value-ref="sqlSessionFactoryCrm2"/>
 *      </map>
 *    </property>
 * </bean>
 * }
 * </pre>
 * 
 * @see org.mybatis.spring.mapper.MapperScannerConfigurer
 * @author spencer.xue
 * @date 2015-7-13
 */
public class MapperBeanFactory implements BeanDefinitionRegistryPostProcessor,InitializingBean,ApplicationContextAware {

	private final Log logger = LogFactory.getLog(MapperBeanFactory.class);
	
	private String basePackage;
	private Map<String,SqlSessionFactory> sqlSessionFactorys;
    // from config	
	private final Map<String,SqlSessionTemplate> server_sqlSession = new HashMap<String,SqlSessionTemplate>();
	private final Set<Class<?>> interfaces = new HashSet<Class<?>>(); 
	// for scan mapper interface
	private ApplicationContext applicationContext;

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public void setSqlSessionFactorys(
			Map<String, SqlSessionFactory> sqlSessionFactorys) {
		this.sqlSessionFactorys = sqlSessionFactorys;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	/**
	 * Retrieves a mapper instance depend on <param>server</param>
	 * @param type <code>MapperClass</code>
	 * @param server crm1,crm2.... from master's database
	 * @return mapper instance of type
	 */
	public <T> T getMapper(Class<T> type, String server) {
		if (!interfaces.contains(type))
		      throw new BindingException("Type " + type + " not be found.");
		SqlSessionTemplate sqlSession = server_sqlSession.get(server);
		if (!sqlSession.getConfiguration().hasMapper(type)) {
			try {
				sqlSession.getConfiguration().addMapper(type);
			} catch (Throwable t) {
				logger.error("Error while adding the mapper '" + type+ "' to configuration.server:"+server, t);
				throw new IllegalArgumentException(t);
			}
		}
		try {
			return sqlSession.getMapper(type);
		} catch (Exception e) {
			throw new BindingException("Error getting mapper instance. Cause: "+ e, e);
		}
	}

	// for check
	@Override
	public void afterPropertiesSet() throws Exception {
		if(basePackage == null){
			notNull(this.basePackage, "Property 'basePackage' is required");	
		}
		if(sqlSessionFactorys == null){
			 notNull(this.basePackage, "Property 'sqlSessionFactorys' is required");
		}
		Set<String> servers = sqlSessionFactorys.keySet();
		for (String server : servers) {
			SqlSessionFactory sqlSessionFactory = sqlSessionFactorys.get(server);
			if(sqlSessionFactory == null){
				notNull(this.basePackage, "sqlSessionFactory for server:"+server+" is null");
			}
			SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory);
			server_sqlSession.put(server,template);
		}
	}

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory paramConfigurableListableBeanFactory)
			throws BeansException {
	}

	@Override
	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry beanDefinitionRegistry)
			throws BeansException {
		Scanner scanner = new Scanner(beanDefinitionRegistry);
	    scanner.setResourceLoader(this.applicationContext);
	    String[] packages = StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
	    Set<BeanDefinitionHolder> beanDefinitions = scanner.doScan(packages);
		for (BeanDefinitionHolder holder : beanDefinitions) {
			GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
			if (logger.isDebugEnabled()) {
				logger.debug("Mapper bean name:"+ holder.getBeanName() 
						+ " mapper interface: "+ definition.getBeanClassName());
			}
			try {
				Class<?> mapperInterface = Class.forName(definition.getBeanClassName());
				interfaces.add(mapperInterface);
				definition.setBeanClass(Object.class);
			} catch (ClassNotFoundException e) {
				throw new NullPointerException("class "+definition.getBeanClassName()+" not be found.");
			}
		}
	}
	
	
	private final class Scanner extends ClassPathBeanDefinitionScanner {

	    public Scanner(BeanDefinitionRegistry registry) {
	      super(registry);
	    }

	    /**
	     * Configures parent scanner to search for the right interfaces. It can search for all
	     * interfaces or just for those that extends a markerInterface or/and those annotated with
	     * the annotationClass
	     */
	    @Override
	    protected void registerDefaultFilters() {
	      boolean acceptAllInterfaces = true;
	      if (acceptAllInterfaces) {
	        // default include filter that accepts all classes
	        addIncludeFilter(new TypeFilter() {
	          public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
	            return true;
	          }
	        });
	      }
	      // exclude package-info.java
	      addExcludeFilter(new TypeFilter() {
	        public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
	          String className = metadataReader.getClassMetadata().getClassName();
	          return className.endsWith("package-info");
	        }
	      });
	    }

	    /**
	     * Calls the parent search that will search and register all the candidates. Then the
	     * registered objects are post processed to set them as MapperFactoryBeans
	     */
	    @Override
	    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
	      Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
	      if (beanDefinitions.isEmpty()) {
	        logger.warn("No MyBatis mapper was found in '" + basePackage
	            + "' package. Please check your configuration.");
	      }
	      return beanDefinitions;
	    }

	    @Override
	    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
	       return (beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent());
	    }

	    @Override
	    protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) throws IllegalStateException {
	      if (super.checkCandidate(beanName, beanDefinition)) {
	        return true;
	      } else {
	        logger.warn("Skipping MapperFactoryBean with name '" + beanName
	            + "' and '" + beanDefinition.getBeanClassName() + "' mapperInterface"
	            + ". Bean already defined with the same name!");
	        return false;
	      }
	    }
		
	}

	
}
