package spring.integration.test.gateway.client;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.file.FileHeaders;

/**
 * 
 * @author dr.ricalde
 * 
 */
public interface FileWriter {

	/**
	 * Write.
	 * 
	 * @param content
	 *            the content
	 * @param fileName
	 *            the file name
	 * @throws Exception
	 *             the exception
	 */
	@Gateway(requestChannel = "filesOut")
	public void write(byte[] content, @Header(FileHeaders.FILENAME) String fileName) throws Exception;

}
