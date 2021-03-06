package com.foo;

import java.util.UUID;

import org.bson.types.ObjectId;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;

@Entity
public class MyEntity {

    @Id
    public ObjectId id;

    @Reference
    public TestEntity reference;

    /**
     * Sets the id and returns the object without setting
     * @return the object
     */
    public static MyEntity getDefault() {
        MyEntity entity = new MyEntity();

        entity.id = ObjectId.get();

        return entity;
    }
}
