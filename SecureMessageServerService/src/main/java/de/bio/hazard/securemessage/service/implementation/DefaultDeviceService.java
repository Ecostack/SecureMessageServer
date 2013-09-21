package de.bio.hazard.securemessage.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.bio.hazard.securemessage.dao.implementation.DefaultDeviceDao;
import de.bio.hazard.securemessage.dao.implementation.DefaultMessageContentDao;
import de.bio.hazard.securemessage.model.Device;
import de.bio.hazard.securemessage.model.User;
import de.bio.hazard.securemessage.service.DeviceService;

@Service(value = "deviceService")
@Transactional(readOnly = true)
public class DefaultDeviceService implements DeviceService {

	@Autowired
	private DefaultDeviceDao deviceDao;

	@Transactional(readOnly = false)
	@Override
	public String addDeviceAndReturnDeviceId(Device pDevice) {
		addDevice(pDevice);
		// TODO SebastianS; Zufallswert
		// XXX NicoH: Sicher, dass du Zufall nutzen möchtest? Auf dem Server sollte eine neue ID aus den bisherigen erzeugt werden können
		return "123";
	}

	@Transactional(readOnly = false)
	@Override
	public void addDevice(Device pDevice) {
		getDeviceDao().create(pDevice);
	}

	@Transactional(readOnly = false)
	@Override
	public void updateDevice(Device pDevice) {
		getDeviceDao().save(pDevice);
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteDevice(Device pDevice) {
		getDeviceDao().remove(pDevice);
	}

	@Override
	public Device getDeviceById(long pID) {
		return getDeviceDao().findByPrimaryKey(pID);
	}

	@Override
	public List<Device> getDevices() {
		return getDeviceDao().findAll();
	}

	@Override
	public List<Device> getDevicesByUser(User pUser) {
		return getDeviceDao().findByUser(pUser);
	}

	public DefaultDeviceDao getDeviceDao() {
		return deviceDao;
	}

	public void setDeviceDao(DefaultDeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}

}
