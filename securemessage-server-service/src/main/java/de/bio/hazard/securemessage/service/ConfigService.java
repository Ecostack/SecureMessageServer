package de.bio.hazard.securemessage.service;

import java.util.List;

import de.bio.hazard.securemessage.model.Config;
import de.bio.hazard.securemessage.service.helper.ConfigType;

public interface ConfigService {

	void addConfig(Config pConfig);

	void updateConfig(Config pConfig);

	void deleteConfig(Config pConfig);

	Config getConfigById(long pID);

	Config getConfigByRunningNumber(int pRunningNumber);

	Config getConfigByEnumType(ConfigType pType);

	List<Config> getConfigs();
}
