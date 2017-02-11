package spring.integration.test.gateway.client.impl;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.FileHeaders;
import org.springframework.stereotype.Component;

import spring.integration.test.gateway.client.FileWriter;

@Component
public class FileWriterService implements FileWriter {

	@Override
	@ServiceActivator
	public void write(byte[] content, @Header(FileHeaders.FILENAME) String fileName) throws Exception {
		FileUtils.writeByteArrayToFile(new File("tmp/" + fileName), content);
	}
}
