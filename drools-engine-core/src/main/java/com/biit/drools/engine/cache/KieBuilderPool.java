package com.biit.drools.engine.cache;

import org.kie.api.builder.KieFileSystem;

import com.biit.utils.pool.SimplePool;

public class KieBuilderPool extends SimplePool<KieFileSystem, PoolableKieBuilder> {
	private static final long EXPIRATION_TIME = 300000;

	@Override
	public boolean isDirty(PoolableKieBuilder element) {
		return false;
	}

	@Override
	public long getExpirationTime() {
		return EXPIRATION_TIME;
	}

}
