package mju.ntj.ejis.commons.util;

import java.util.logging.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.webflow.context.servlet.ServletExternalContext;
import org.springframework.webflow.execution.RequestContext;

import eu.medsea.mimeutil.MimeUtil;

@Component("fileUtils")
public class FileUtils {

	/** The Logger. */
	private static final Logger log = Logger.getLogger("FileUtils");

	public void writeFileToServletResponse(byte[] content, String fileName, RequestContext context) {
		try {
			MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
			HttpServletResponse response = (HttpServletResponse) ((ServletExternalContext) context.getExternalContext())
					.getNativeResponse();
			response.setContentType(MimeUtil.getMimeTypes(content).toString());
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-control", "private");
			ServletOutputStream out = response.getOutputStream();
			out.write(content);
			out.flush();
			out.close();
			context.getExternalContext().recordResponseComplete();
		} catch (Exception e) {
			log.severe("Error al escribir el fichero al servlet response: " + e.getMessage());
		}
	}
}
