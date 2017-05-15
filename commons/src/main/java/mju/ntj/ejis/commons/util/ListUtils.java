package mju.ntj.ejis.commons.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Dante Ricalde
 * 
 */
@Component
public class ListUtils {

	/**
	 * To long list.
	 * 
	 * @param list
	 *            the list
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<Long> toLongList(List<String> list) {
		List<Long> result = null;

		if (CollectionUtils.isNotEmpty(list)) {
			result = new ArrayList<Long>(CollectionUtils.collect(list, new Transformer() {
				public Object transform(Object input) {
					return Long.valueOf((String) input);
				}
			}));
		}

		return result;
	}

}
