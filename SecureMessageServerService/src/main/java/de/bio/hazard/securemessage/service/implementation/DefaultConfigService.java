package de.bio.hazard.securemessage.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.bio.hazard.securemessage.dao.implementation.DefaultConfigDao;
import de.bio.hazard.securemessage.model.Config;
import de.bio.hazard.securemessage.service.ConfigService;

@Service(value = "configService")
@Transactional(readOnly = true)
public class DefaultConfigService implements ConfigService {

	@Autowired
	private DefaultConfigDao ConfigDao;

	@Transactional(readOnly = false)
	@Override
	public void addConfig(Config pConfig) {
		getConfigDao().create(pConfig);
	}

	@Transactional(readOnly = false)
	@Override
	public void updateConfig(Config pConfig) {
		getConfigDao().save(pConfig);
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteConfig(Config pConfig) {
		getConfigDao().remove(pConfig);
	}

	@Override
	public Config getConfigById(long pID) {
		return getConfigDao().findByPrimaryKey(pID);
	}

	@Override
	public List<Config> getConfigs() {
		return getConfigDao().findAll();
	}

	public DefaultConfigDao getConfigDao() {
		return ConfigDao;
	}

	public void setConfigDao(DefaultConfigDao ConfigDao) {
		this.ConfigDao = ConfigDao;
	}

}
