package ep.nci.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement
@Entity
@Table(name = "CORUSEROTP")
public class CorUserOtp extends AbstractModel {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue
	private Integer userId;
	
	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;

	@Column(name = "OTP")
	private String otp;
	
	@Column(name = "POSDEV_OTPSENTDT")
	private Date posDevOtpSentDatetime;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Date getPosDevOtpSentDatetime() {
		return posDevOtpSentDatetime;
	}

	public void setPosDevOtpSentDatetime(Date posDevOtpSentDatetime) {
		this.posDevOtpSentDatetime = posDevOtpSentDatetime;
	}

	@Override
	public String toString() {
		return "CorUserOtp [userId=" + userId + ", mobileNumber="
				+ mobileNumber + ", otp=" + otp + ", posDevOtpSentDatetime="
				+ posDevOtpSentDatetime + "]";
	}	
	
	
}
