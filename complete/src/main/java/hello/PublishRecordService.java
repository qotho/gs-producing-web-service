package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import gov.ic.dia.wiseism.webservices.domain.publish.PublishEval;
import gov.ic.dia.wiseism.webservices.domain.publish.PublishEvalResponse;
import gov.ic.dia.wiseism.webservices.domain.publish.PublishRequestObject;
import gov.ic.dia.wiseism.webservices.exception.DomElementException;

@Endpoint
public class PublishRecordService {
	
	private static final String NAMESPACE_URI = "http://wiseism.dia.ic.gov/webservices/publishXml/v3";

	private XmlRepository xmlRepository;

	@Autowired
	public PublishRecordService(XmlRepository xmlRepository) {
		this.xmlRepository = xmlRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "publishEvalRecord")
	@ResponsePayload
	public PublishEvalResponse publishEvalRecord(@RequestPayload PublishEval request) {
		PublishRequestObject ro = (PublishRequestObject) request.getRequestObjects().get(0);
		
		try {
			System.out.println(ro.getDocumentAsString());
		} catch (DomElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		DOMImplementationLS domImplLS = (DOMImplementationLS) document
		    .getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		System.out.println(serializer.writeToString(request));
		*/
		
		return new PublishEvalResponse();
	}

}
