package de.bio.hazard.securemessage.service.implementation;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.bio.hazard.securemessage.dao.implementation.DefaultConfigDao;
import de.bio.hazard.securemessage.model.Config;
import de.bio.hazard.securemessage.service.ConfigService;
import de.bio.hazard.securemessage.service.helper.ConfigType;

@Service(value = "configService")
@Transactional(readOnly = true)
public class DefaultConfigService implements ConfigService {

	private ConcurrentHashMap<ConfigType, Config> configList = new ConcurrentHashMap<ConfigType, Config>();

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

	public synchronized Config getConfigByEnumType(ConfigType pType) {
		Config lcReturn = null;
		if (configList.contains(pType)) {
			lcReturn = configList.get(pType);
		} else {
			configList.put(pType,
					getConfigByRunningNumber(pType.getRunningNumber()));
			lcReturn = configList.get(pType);
		}
		return lcReturn;
	}

	public DefaultConfigDao getConfigDao() {
		return ConfigDao;
	}

	public void setConfigDao(DefaultConfigDao ConfigDao) {
		this.ConfigDao = ConfigDao;
	}

	@Override
	public Config getConfigByRunningNumber(int pRunningNumber) {
		return getConfigDao().findConfigByRunningNumber(pRunningNumber);
	}

}
