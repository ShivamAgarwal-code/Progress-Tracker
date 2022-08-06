package com.oppurtunity.hack.Controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.oppurtunity.hack.entities.Module;
import com.oppurtunity.hack.entities.ModuleWrapper;

@RestController
public class ObjectCollectionController {

    @Autowired
    private MongoClient mongoClient;

    @RequestMapping(value="/create_object", consumes = "application/json")
    public ResponseEntity createCollection(@RequestBody ModuleWrapper objects) {
        try{
        DB database = mongoClient.getDB("progresstracking-objects");
        DBCollection collection = database.createCollection(objects.getModuleName(), null);
        System.out.println(objects.getModuleName());
        BasicDBObject document = new BasicDBObject();
        for(Module mod : objects.getAttributes()) {
            document.put(mod.getLabel(), "test");
        }
        collection.insert(document);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    }

    @RequestMapping(value="/get_object", method=RequestMethod.GET)
    public Set<String> getCollection(@RequestParam("collectionName") String collectionName) {
        DB database = mongoClient.getDB("progresstracking-objects");
        System.out.println(collectionName);
        DBCollection collection = database.getCollection(collectionName);
        Set<String> set = collection.findOne().keySet();
        Set<String> output = new HashSet<>();
        for(String s: set) {
            if(!s.equals("_id")) {
                output.add(s);
            }
        }
        return output;
    }

    @RequestMapping("/delete_objects")
    public void deleteCollections() {
        DB database = mongoClient.getDB("progresstracking-objects");
        database.dropDatabase();
    }

}
