package ep.nci.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "DEVICEMST")
public class DeviceMst  {
	

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "DEV_ID")
	@GeneratedValue
	private Integer deviceId;
	
	@Column(name = "DEV_NAME")
	private String devName;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "DEV_STATUS")
	private CorControlDtl devStatus;
	
	@Column(name = "DEV_OS")
	private String devOs;
	
	@Column(name = "DEV_MOBILE_NUMBER")
	private String mobileNumber;
	
	@Column(name = "DEV_OSVERSION")
	private String devOsVersion;

	@Column(name = "DEV_MACADDR")
	private String devMacAddr;

	/**
	 * Added for verification of token for calling the services
	 * */
	@Column(name = "DEV_TOKEN" , length = 500)
	private String devToken;

	/**
	 * Added for OTP function
	 * */
	@Column(name = "DEV_OTP")
	private Integer devOtp;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "DEV_OTPSTATUS")
	private CorControlDtl devOtpStatus;

	@Column(name = "DEV_OTPATTEMPT")
	private Integer devOtpAttempt;
	
	@Column(name = "POSDEV_OTPSENTDT")
	private Date posDevOtpSentDatetime;
	
	@Column(name = "POSDEV_OTPUPDATEDDT")
	private Date updatedDatetime;
	
	@Column(name = "IP_ADDRESS")
	private String IpAddress;

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	public CorControlDtl getDevStatus() {
		return devStatus;
	}

	public void setDevStatus(CorControlDtl devStatus) {
		this.devStatus = devStatus;
	}

	public String getDevOs() {
		return devOs;
	}
	
	public void setDevMobileNumber(String mobileNumber){
		this.mobileNumber = mobileNumber;
	}
	
	public String getDevMobileNumber(){
		return mobileNumber;
	}

	public void setDevOs(String devOs) {
		this.devOs = devOs;
	}

	public String getDevOsVersion() {
		return devOsVersion;
	}

	public void setDevOsVersion(String devOsVersion) {
		this.devOsVersion = devOsVersion;
	}

	public String getDevMacAddr() {
		return devMacAddr;
	}

	public void setDevMacAddr(String devMacAddr) {
		this.devMacAddr = devMacAddr;
	}
	
	public void setIpAddres(String IpAddress) {
		this.IpAddress = IpAddress;
	}

	public String getDevToken() {
		return devToken;
	}

	public void setDevToken(String devToken) {
		this.devToken = devToken;
	}

	public Integer getDevOtp() {
		return devOtp;
	}

	public void setDevOtp(Integer devOtp) {
		this.devOtp = devOtp;
	}

	public CorControlDtl getDevOtpStatus() {
		return devOtpStatus;
	}

	public void setDevOtpStatus(CorControlDtl devOtpStatus) {
		this.devOtpStatus = devOtpStatus;
	}

	public Integer getDevOtpAttempt() {
		return devOtpAttempt;
	}

	public void setDevOtpAttempt(Integer devOtpAttempt) {
		this.devOtpAttempt = devOtpAttempt;
	}

	public Date getPosDevOtpSentDatetime() {
		return posDevOtpSentDatetime;
	}
	
	public void setPosDevOtpSentDatetime(Date posDevOtpSentDatetime) {
		this.posDevOtpSentDatetime = posDevOtpSentDatetime;
	}
	
	public Date getPosDevOtpUpdatedDatetime() {
		return updatedDatetime;
	}
	
	public void setPosDevOtpUpdatedDatetime(Date updatedDatetime) {
		this.updatedDatetime =updatedDatetime;
	}

	@Override
	public String toString() {
		return "DeviceMst [deviceId=" + deviceId + ", devName=" + devName + ",devStatus="
				+ devStatus + ", devOs=" + devOs + ", devOsVersion=" + devOsVersion + ", devMacAddr=" + devMacAddr
				+ ", devToken=" + devToken + ", devOtp=" + devOtp + ", devOtpStatus=" + devOtpStatus
				+ ", devOtpAttempt=" + devOtpAttempt + ", posDevOtpSentDatetime=" + posDevOtpSentDatetime + "]";
	}


}
