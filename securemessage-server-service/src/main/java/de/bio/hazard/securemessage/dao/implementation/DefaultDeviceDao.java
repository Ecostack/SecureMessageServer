package de.bio.hazard.securemessage.dao.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.db.jpa.dao.AbstractGenericJpaDAO;
import de.bio.hazard.securemessage.model.Device;
import de.bio.hazard.securemessage.model.User;

@Component
public class DefaultDeviceDao extends AbstractGenericJpaDAO<Device, Long> {

	public Long getPrimaryKey(Device persistentObject) {
		return persistentObject.getId();
	}

	@Override
	public List<Device> findAll() {
		return getEntityManager().createNamedQuery(Device.FIND_ALL,
				Device.class).getResultList();
	}

	public List<Device> findByUser(User pUser) {
		return getEntityManager()
				.createNamedQuery(Device.FIND_BY_USER, Device.class)
				.setParameter(1, pUser).getResultList();
	}

	public Device findByDeviceId(String pDeviceId) {
		return getEntityManager()
				.createNamedQuery(Device.FIND_BY_DEVICE_ID, Device.class)
				.setParameter(1, pDeviceId).getSingleResult();
	}

	public long countByDeviceId(String pDeviceId) {
		return getEntityManager()
				.createNamedQuery(Device.COUNT_BY_DEVICE_ID, Long.class)
				.setParameter(1, pDeviceId).getSingleResult();
	}

}
