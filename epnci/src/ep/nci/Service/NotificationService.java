package ep.nci.Service;

public interface NotificationService {
	public Boolean sendNotification(String contactNo,String message);
	public Boolean isEnabled();
}
