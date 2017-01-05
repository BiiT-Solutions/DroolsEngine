package com.biit.drools.engine.cache;

import org.kie.api.builder.KieFileSystem;

import com.biit.drools.configuration.DroolsEngineConfigurationReader;
import com.biit.utils.pool.SimplePool;

public class KieBuilderPool extends SimplePool<KieFileSystem, PoolableKieBuilder> {

	@Override
	public boolean isDirty(PoolableKieBuilder element) {
		return false;
	}

	@Override
	public long getExpirationTime() {
		return DroolsEngineConfigurationReader.getInstance().getPoolExpirationTime();
	}

}
