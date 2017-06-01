/**************************************************************************************************
 * File Name : CORCONTROLDTL.java
 * @author   : KRISHNA
 * Version Information : 1.0
 * Model for CORCONTROLDTL
 * Modification History:
 * Date		 Modified By		Description 
 ************************************************************************************************* */

package ep.nci.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



/**
 * Model to use the Control Detail table.
 * @author Krishna
 */


@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@Entity
@EPSort(orderSequence = {"controlDtlSeq"})
@Table(name = "CORCONTROLDTL")
public class CorControlDtl extends AbstractModel {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "CONTROLDTL_ID")
	@GeneratedValue
	private Integer controlDtlId;

	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name = "CONTROLDTL_REF_ID")
	private CorControlHdr controlDtlRefId;

	//@NameColumn
	@Column(name = "CONTROLDTL_NAME")
	private String controlDtlName;
	
	@Column(name = "CONTROLDTL_CODE")
	private String controlDtlCode;
	
	/** introduced for CHILD rows in same table
	 * like. store 'CARD' value in this field of a row of PaymentMode.CREDIT-CARD
	 *  */
	@Column(name = "CONTROLDTL_PARENT")
	private String controlDtlParent;	
	
	@Column(name = "CONTROLDTL_SEQ")
	private Integer controlDtlSeq;

	@XmlElement
	public Integer getControlDtlId() {
		return controlDtlId;
	}

	public void setControlDtlId(Integer controlDtlId) {
		this.controlDtlId = controlDtlId;
	}

	@XmlElement
	public CorControlHdr getControlDtlRefId() {
		return controlDtlRefId;
	}

	public void setControlDtlRefId(CorControlHdr controlDtlRefId) {
		this.controlDtlRefId = controlDtlRefId;
	}

	@XmlElement
	public String getControlDtlName() {
		return controlDtlName;
	}

	public void setControlDtlName(String controlDtlName) {
		this.controlDtlName = controlDtlName;
	}

	public String getControlDtlCode() {
		return controlDtlCode;
	}

	public void setControlDtlCode(String controlDtlCode) {
		this.controlDtlCode = controlDtlCode;
	}	
	
	public String getControlDtlParent() {
		return controlDtlParent;
	}

	public void setControlDtlParent(String controlDtlParent) {
		this.controlDtlParent = controlDtlParent;
	}

	public Integer getControlDtlSeq() {
		return controlDtlSeq;
	}

	public void setControlDtlSeq(Integer controlDtlSeq) {
		this.controlDtlSeq = controlDtlSeq;
	}
	
	
}
