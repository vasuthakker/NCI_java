/**************************************************************************************************
 * File Name : CORCONTROLHDR.java
 * @author   : KRISHNA
 * Version Information : 1.0
 * Model for CORCONTROLHDR
 * Modification History:
 * Date		 Modified By		Description 
 ************************************************************************************************* */

package ep.nci.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

import ep.nci.model.AbstractModel;


@Entity
@Table(name = "CORCONTROLHDR")
public class CorControlHdr extends AbstractModel {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CONTROL_ID")
	@GeneratedValue
	private Integer controlId;

	@Column(name = "CONTROL_CATEGORY")
	private String controlCategory;

	@XmlElement
	public Integer getControlId() {
		return controlId;
	}

	public void setControlId(Integer controlId) {
		this.controlId = controlId;
	}

	@XmlElement
	public String getControlCategory() {
		return controlCategory;
	}

	public void setControlCategory(String controlCategory) {
		this.controlCategory = controlCategory;
	}
}
