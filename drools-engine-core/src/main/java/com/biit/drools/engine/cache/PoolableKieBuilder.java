package com.biit.drools.engine.cache;

import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;

import com.biit.utils.pool.PoolElement;

public class PoolableKieBuilder implements PoolElement<KieFileSystem> {

	private KieFileSystem id;
	private KieBuilder kieBuilder;

	public PoolableKieBuilder(KieFileSystem id, KieBuilder kieBuilder) {
		this.id = id;
		this.kieBuilder = kieBuilder;
	}

	@Override
	public KieFileSystem getId() {
		return id;
	}

	public KieBuilder getKieBuilder() {
		return kieBuilder;
	}

}
