package org.ttang;

import javax.inject.Singleton;

import org.ttang.objectstore.ObjectStore;

@Singleton
public class UserTaskStore extends ObjectStore<String,UserTask>{
}
