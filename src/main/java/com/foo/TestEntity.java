package com.foo;

import org.bson.types.ObjectId;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;

@Entity
public class TestEntity {

    @Id
    public ObjectId id;

    @Reference
    public MyEntity backReference;

    /**
     * Sets the id and returns the object without setting
     * @return the object
     */
    public static TestEntity getDefault() {
        TestEntity entity = new TestEntity();

        entity.id = ObjectId.get();

        return entity;
    }
}
