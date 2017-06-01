package ep.nci.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * All easypay app model objects extends this class. 
 * @author Vishal
 *
 */
@MappedSuperclass
public abstract class AbstractModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MMM-yy HH:mm:ss",timezone="IST")
	@Column(name = "CREATED_DT")
	protected Date createdDt;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MMM-yy HH:mm:ss",timezone="IST")
	@Column(name = "UPDATED_DT")
	protected Date updatedDt;

	@Column(name = "CREATED_BY")
	protected Long createdBy;

	@Column(name = "UPDATED_BY")
	protected Long updatedBy;
	
	/**
	 * Use this Status enum in respective controller classes for 
	 * setting status of a record
	 */
	@Column(name = "STATUS")
	protected Integer status;

	@Column(name="VERSION_NO")
	protected Long versionNo;
	
	// This field has been added for auditing purpose to retain the older status value
	@Transient
	protected Integer olderStatus;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public Date getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	//public AbstractHistoryModel oldVersion(AbstractModel oldObj){
	//	return null;
	//}

	
	public Integer getOlderStatus() {
		return olderStatus;
	}

	public void setOlderStatus(Integer olderStatus) {
		this.olderStatus = olderStatus;
	}
}
