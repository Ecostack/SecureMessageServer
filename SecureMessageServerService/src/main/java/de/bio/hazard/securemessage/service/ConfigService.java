package de.bio.hazard.securemessage.service;

import java.util.List;

import de.bio.hazard.securemessage.model.Config;

public interface ConfigService {

	void addConfig(Config pConfig);

	void updateConfig(Config pConfig);

	void deleteConfig(Config pConfig);

	Config getConfigById(long pID);

	List<Config> getConfigs();
}
