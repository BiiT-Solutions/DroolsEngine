package com.biit.drools.engine.cache;

import org.kie.api.builder.KieBuilder;

import com.biit.utils.pool.PoolElement;

public class PoolableKieBuilder implements PoolElement<Integer> {

	private Integer id;
	private KieBuilder kieBuilder;

	public PoolableKieBuilder(Integer id, KieBuilder kieBuilder) {
		this.id = id;
		this.kieBuilder = kieBuilder;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public KieBuilder getKieBuilder() {
		return kieBuilder;
	}

}
