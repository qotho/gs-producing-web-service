package hello;

import java.util.List;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jibx.JibxMarshaller;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.endpoint.adapter.method.MarshallingPayloadMethodProcessor;
import org.springframework.ws.server.endpoint.adapter.method.MethodArgumentResolver;
import org.springframework.ws.server.endpoint.adapter.method.MethodReturnValueHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

@EnableWs()
//@ComponentScan(basePackageClasses = { PublishRecordService.class })
@Configuration
@ImportResource("classpath*:marshallingContext.xml")
public class WebServiceConfig extends WsConfigurerAdapter {

	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		//servlet.setTransformWsdlLocations(true);
		
		return new ServletRegistrationBean(servlet, "/services/*");
	}
  
	@Bean
	public Marshaller marshaller() {
		JibxMarshaller marshaller = new JibxMarshaller();
		marshaller.setTargetPackage("gov.ic.dia.wiseism.webservices.domain.publish.iqueue");
		marshaller.setBindingName("publish_binding");
		
		return marshaller;
	}

	@Bean
	public MarshallingPayloadMethodProcessor marshallingPayloadMethodProcessor() {
		return new MarshallingPayloadMethodProcessor(marshaller());
	}

	@Override
	public void addArgumentResolvers(List<MethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(marshallingPayloadMethodProcessor());
	}

	@Override
	public void addReturnValueHandlers(List<MethodReturnValueHandler> returnValueHandlers) {
		returnValueHandlers.add(marshallingPayloadMethodProcessor());
	}
	
}
