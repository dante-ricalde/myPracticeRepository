package spring.integration.test.config.ftp;

import java.util.regex.Pattern;

import org.apache.commons.net.ftp.FTPFile;
import org.springframework.integration.file.filters.AbstractRegexPatternFileListFilter;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class CustomRegexPatternFileListFilter extends AbstractRegexPatternFileListFilter<FTPFile> {

	public CustomRegexPatternFileListFilter(String pattern) {
		super(pattern);
		// TODO Auto-generated constructor stub
	}

	public CustomRegexPatternFileListFilter(Pattern pattern) {
		super(pattern);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getFilename(FTPFile file) {
		// TODO Auto-generated method stub
		return (file != null) ? file.getName() : null;
	}

}
