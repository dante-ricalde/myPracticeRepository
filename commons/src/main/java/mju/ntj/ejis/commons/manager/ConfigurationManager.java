package mju.ntj.ejis.commons.manager;

import java.util.Properties;
import java.util.logging.Logger;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class ConfigurationManager {

	/** The Logger. */
	private static final Logger log = Logger.getLogger("ConfigurationManager");

	Properties dataProperties;

	/**
	 * Gets the data value.
	 * 
	 * @param dataKey
	 *            the data key
	 * @return the data value
	 */
	public String getDataValue(String dataKey) {
		String result = null;

		try {
			result = getDataProperties().getProperty(dataKey);
			if (result.contains("${")) {
				int inicio = result.indexOf("${");
				int fin = result.indexOf("}");
				result = result.substring(0, inicio).concat(this.getDataValue(result.substring(inicio + 2, fin)))
						.concat(result.substring(fin + 1, result.length()));
			}
		} catch (Exception e) {
			log.severe("Datakey no existe: " + dataKey);
		}

		return result;
	}

	public String getDataValueWithoutErrorLogIfNotExists(String dataKey) {
		String result = null;

		try {
			result = getDataValue(dataKey);
		} catch (Exception e) {
		}

		return result;
	}

	/**
	 * Gets the exception properties.
	 * 
	 * @return the exception properties
	 */
	public Properties getDataProperties() {
		return dataProperties;
	}

	/**
	 * Sets the exception properties.
	 * 
	 * @param dataProperties
	 *            the new exception properties
	 */
	public void setDataProperties(Properties dataProperties) {
		this.dataProperties = dataProperties;
	}
}
