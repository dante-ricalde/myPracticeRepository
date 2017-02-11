package spring.integration.test.handler.serviceActivator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.http.multipart.UploadedMultipartFile;
import org.springframework.util.LinkedMultiValueMap;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class MultipartReceiverServiceActivator {

	private static final Logger LOGGER = LoggerFactory.getLogger(MultipartReceiverServiceActivator.class);

	public void receive(LinkedMultiValueMap<String, Object> multipartRequest) {
		LOGGER.info("### Successfully received multipart request ###");
		for (String elementName : multipartRequest.keySet()) {
			if (elementName.equals("company")) {
				LOGGER.info("\t" + elementName + " - " + ((String[]) multipartRequest.getFirst("company"))[0]);
			} else if (elementName.equals("company-logo")) {
				LOGGER.info("\t" + elementName + " - as UploadedMultipartFile: "
						+ ((UploadedMultipartFile) multipartRequest.getFirst("company-logo")).getOriginalFilename());
			}
		}
	}
}
