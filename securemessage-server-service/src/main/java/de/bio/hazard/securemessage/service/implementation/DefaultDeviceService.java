package de.bio.hazard.securemessage.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.bio.hazard.securemessage.dao.implementation.DefaultDeviceDao;
import de.bio.hazard.securemessage.model.Device;
import de.bio.hazard.securemessage.model.User;
import de.bio.hazard.securemessage.service.DeviceService;
import de.bio.hazard.securemessage.tecframework.data.IdGenerator;

@Service(value = "deviceService")
@Transactional(readOnly = true)
public class DefaultDeviceService implements DeviceService {

	@Autowired
	private DefaultDeviceDao deviceDao;

	@Autowired
	private IdGenerator idGenerator;

	@Transactional(readOnly = false)
	public String findNewDeviceId() {
		String lcNewId = null;
		long lcDeviceCount = 1;
		do {
			lcNewId = idGenerator.nextId();
			lcDeviceCount = getCountByDeviceId(lcNewId);
		} while (lcDeviceCount != 0);

		return lcNewId;
	}

	@Transactional(readOnly = false)
	@Override
	public String addDeviceAndReturnDeviceId(Device pDevice) {
		pDevice.setDeviceId(findNewDeviceId());
		addDevice(pDevice);
		return pDevice.getDeviceId();
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

	@Override
	public Device getDeviceByDeviceId(String pDeviceId) {
		return getDeviceDao().findByDeviceId(pDeviceId);
	}

	public DefaultDeviceDao getDeviceDao() {
		return deviceDao;
	}

	public void setDeviceDao(DefaultDeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}

	@Override
	public long getCountByDeviceId(String pDeviceId) {
		return getDeviceDao().countByDeviceId(pDeviceId);
	}

}
