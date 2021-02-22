package com.foo;

import java.util.List;

import com.antwerkz.bottlerocket.BottleRocket;
import com.antwerkz.bottlerocket.BottleRocketTest;
import com.github.zafarkhaja.semver.Version;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReproducerTest extends BottleRocketTest {
    private static final Logger LOG = LoggerFactory.getLogger(ReproducerTest.class);
    
    private Datastore datastore;

    public ReproducerTest() {
        MongoClient mongo = getMongoClient();
        MongoDatabase database = getDatabase();
        database.drop();
        datastore = Morphia.createDatastore(mongo, getDatabase().getName());

        datastore.getMapper().map(MyEntity.class, TestEntity.class);
    }

    @NotNull
    @Override
    public String databaseName() {
        return "morphia_repro";
    }

    @Nullable
    @Override
    public Version version() {
        return BottleRocket.DEFAULT_VERSION;
    }

    @Test
    public void reproduce() {
        //creating test entries
        this._createTestEntities();

        //find first MyEntity
        MyEntity foundEntity = datastore.find(MyEntity.class).first();

        if(foundEntity != null) {
            LOG.info("foundEntity id is " + foundEntity.id);
        } else {
            LOG.info("foundEntity is null");
        }
    }

    private void _createTestEntities() {
        MyEntity newMyEntity = MyEntity.getDefault();

        TestEntity newTestEntity = TestEntity.getDefault();

        newMyEntity.reference = newTestEntity;
        newTestEntity.backReference = newMyEntity;

        datastore.save(newMyEntity);
        datastore.save(newTestEntity);
    }
}
