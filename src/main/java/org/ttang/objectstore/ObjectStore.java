package org.ttang.objectstore;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import com.rits.cloning.Cloner;

public class ObjectStore<K,T extends ObjectStoreItem<K>> {

	private ConcurrentHashMap<K,T> store = new ConcurrentHashMap<K,T>();
	private Cloner cloner = new Cloner();

	public T get(K key) {
		T storeItem = store.get(key);
		return storeItem == null ? null : cloner.deepClone(storeItem);
	}

	public T put(T item) {
		K key = item.getKey();
		T storeItem = store.get(key);

		if (storeItem == null) {
			T newItem = cloner.deepClone(item);
			newItem.changeRevisionId();
			return store.putIfAbsent(key, newItem) == null ? newItem : null;

		} else if (storeItem.getRevisionId().equals(item.getRevisionId())) {
			T newItem = cloner.deepClone(item);
			newItem.changeRevisionId();
			return store.replace(key, storeItem, newItem) ? newItem : null;
		}

		return null;
	}
	
	public Collection<T> getAll() {
		return store.values();
	}
}
