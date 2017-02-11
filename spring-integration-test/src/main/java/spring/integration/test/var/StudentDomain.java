package spring.integration.test.var;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class StudentDomain.
 * 
 * @author dr.ricalde
 */
@Entity(name = "Student")
@Table(name = "STUDENT")
public class StudentDomain implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7882997461456874990L;

	@Id
	private String id;

	private Long rollNumber;

	private String name;

	private String firstName;

	/**
	 * Instantiates a new student domain.
	 */
	public StudentDomain() {
		super();
	}

	/**
	 * Instantiates a new student domain.
	 * 
	 * @param id
	 *            the id
	 * @param name
	 *            the name
	 * @param firstName
	 *            the first name
	 */
	public StudentDomain(String id, String name, String firstName) {
		super();
		this.id = id;
		this.name = name;
		this.firstName = firstName;
	}

	/**
	 * Instantiates a new student domain.
	 *
	 * @param id the id
	 * @param rollNumber the roll number
	 * @param name the name
	 * @param firstName the first name
	 */
	public StudentDomain(String id, Long rollNumber, String name, String firstName) {
		super();
		this.id = id;
		this.rollNumber = rollNumber;
		this.name = name;
		this.firstName = firstName;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the rollNumber
	 */
	public Long getRollNumber() {
		return rollNumber;
	}

	/**
	 * @param rollNumber
	 *            the rollNumber to set
	 */
	public void setRollNumber(Long rollNumber) {
		this.rollNumber = rollNumber;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String toString() {
		return "StudentDomain [id=" + id + ", rollNumber=" + rollNumber + ", name=" + name + ", firstName=" + firstName
				+ "]";
	}
}
