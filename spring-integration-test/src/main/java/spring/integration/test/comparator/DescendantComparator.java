package spring.integration.test.comparator;

import java.io.Serializable;
import java.util.Comparator;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * @param <T>
 * 
 */
@Component("descendantComparator")
public class DescendantComparator<M extends Message<T>, T extends Serializable> implements Comparator<M> {

	@Override
	public int compare(M o1, M o2) {
		return String.valueOf(o2.getPayload()).compareTo(String.valueOf(o1.getPayload()));
	}

}
