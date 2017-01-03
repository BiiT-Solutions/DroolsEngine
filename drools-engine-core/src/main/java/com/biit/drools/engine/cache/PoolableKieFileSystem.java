package com.biit.drools.engine.cache;

import org.kie.api.builder.KieFileSystem;

import com.biit.utils.pool.PoolElement;

public class PoolableKieFileSystem implements PoolElement<Integer> {
	private Integer id;
	private KieFileSystem kieFileSystem;

	public PoolableKieFileSystem(Integer id, KieFileSystem kieFileSystem) {
		this.id = id;
		this.kieFileSystem = kieFileSystem;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public KieFileSystem getKieFileSystem() {
		return kieFileSystem;
	}

}
