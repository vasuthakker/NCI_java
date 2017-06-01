package ep.nci.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ep.nci.model.*;

@Entity
@Table(name = "DEVICEUSERMAPPING")
public class DeviceUserMapping  {
	
	
	@Id
	@Column(name = "DEVICE_USER_ID")
	@GeneratedValue
	private Long id;
	
	@Column(name = "MOBILE_NO")
	private String mobileNo;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "DEVICE_ID")
	private DeviceMst deviceId;
	
	@Column(name = "M_PIN")
	private String mPin;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "PIN_STATUS")
	private CorControlDtl pinStatus;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "SECURITY_QUES")
	private CorControlDtl securityQues;
	
	@Column(name = "SECURITY_ANS")
	private String securityAns;
	
	@Column(name = "PUSH_NOTICATION_TOKEN",length=500)
	private String pushToken;
	
	@Column(name = "APP_STATUS")
	private String appStatus;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DeviceMst getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(DeviceMst deviceId) {
		this.deviceId = deviceId;
	}

	public CorControlDtl getPinStatus() {
		return pinStatus;
	}

	public void setPinStatus(CorControlDtl pinStatus) {
		this.pinStatus = pinStatus;
	}
	
	public CorControlDtl getSecurityQues() {
		return securityQues;
	}

	public void setSecurityQues(CorControlDtl securityQues) {
		this.securityQues = securityQues;
	}

	public String getSecurityAns() {
		return securityAns;
	}

	public void setSecurityAns(String securityAns) {
		this.securityAns = securityAns;
	}
	
	public String getPushToken() {
		return pushToken;
	}

	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}
	
	public String getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	public String getPIN() {
		return mPin;
	}
	
	public void setPIN(String mPin) {
		this.mPin = mPin;
	}

	@Override
	public String toString() {
		return "DeviceUserMapping [id=" + id + ", mobileNo=" + mobileNo + ", deviceId=" + deviceId + ", pinStatus=" + pinStatus
				+ ", securityQues=" + securityQues + ", securityAns=" + securityAns + ", pushToken=" + pushToken
				+ ", appStatus=" + appStatus + "]";
	}

}
