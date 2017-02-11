package myproject.domain;

/**
 * 
 * @author dr.ricalde
 *
 */
public class Project {
	
	private String name;
	
	private String description;
	
	private String site;
	
	private Long id;
	
	public Project() {
		
	}

	public Project(Long id, String name, String description, String site) {
		super();
		this.id=id;
		this.name = name;
		this.description = description;
		this.site = site;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the site
	 */
	public String getSite() {
		return site;
	}

	/**
	 * @param site the site to set
	 */
	public void setSite(String site) {
		this.site = site;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Project [name=" + name + ", description=" + description + ", site=" + site + ", id=" + id + "]";
	}
	
}
