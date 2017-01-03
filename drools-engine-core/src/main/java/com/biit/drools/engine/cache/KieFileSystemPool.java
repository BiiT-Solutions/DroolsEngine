package com.biit.drools.engine.cache;

import com.biit.utils.pool.SimplePool;

public class KieFileSystemPool extends SimplePool<Integer, PoolableKieFileSystem> {
	private static final long EXPIRATION_TIME = 3600000;

	@Override
	public boolean isDirty(PoolableKieFileSystem element) {
		return false;
	}

	@Override
	public long getExpirationTime() {
		return EXPIRATION_TIME;
	}

}
