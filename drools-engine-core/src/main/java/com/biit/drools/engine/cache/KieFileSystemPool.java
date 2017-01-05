package com.biit.drools.engine.cache;

import com.biit.drools.configuration.DroolsEngineConfigurationReader;
import com.biit.utils.pool.SimplePool;

public class KieFileSystemPool extends SimplePool<Integer, PoolableKieFileSystem> {

	@Override
	public boolean isDirty(PoolableKieFileSystem element) {
		return false;
	}

	@Override
	public long getExpirationTime() {
		return DroolsEngineConfigurationReader.getInstance().getPoolExpirationTime();
	}

}
