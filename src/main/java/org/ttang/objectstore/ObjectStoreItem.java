package org.ttang.objectstore;

import java.util.UUID;

import org.ttang.quality.Package;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class ObjectStoreItem<K> {
	/*
	 * revisionId for detecting update race conditions.
	 */

	private UUID revisionId = UUID.randomUUID();

	/*
	 * This should only be called by the DataStore when an item is checked in
	 */
	@Package void changeRevisionId() {
		revisionId = UUID.randomUUID();
	}

	@JsonIgnore
	public UUID getRevisionId() {
		return revisionId;
	}
	
	@JsonIgnore
	public abstract K getKey();
}
	