package com.oppurtunity.hack.Controllers;

import com.mongodb.*;
import com.oppurtunity.hack.entities.Module;
import com.oppurtunity.hack.entities.ModuleWrapper;
import com.oppurtunity.hack.entities.ObjectDataModule;
import com.oppurtunity.hack.entities.ObjectDataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/object")
public class PushObjectController {

    @Autowired
    private MongoClient mongoClient;

    @RequestMapping(value="/createObject", consumes = "application/json")

    public ResponseEntity createCollection(@RequestBody ObjectDataWrapper objects) {
        DB database = mongoClient.getDB("progresstracking-objects");
        DBCollection collection = database.getCollection(objects.getModuleName());
        BasicDBObject document = new BasicDBObject();
        for(ObjectDataModule mod : objects.getAttributes()) {
            document.put(mod.getLabel(), mod.getValue());
        }
        collection.insert(document);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping("/getObjectDetails")
    public HashMap<String, Set<String>> getCollection(@RequestParam("moduleName") String moduleName) {
        DB database = mongoClient.getDB("progresstracking-objects");
        DBCollection collection = database.getCollection(moduleName);
        Set<String> set = collection.findOne().keySet();
        Set<String> output = new HashSet<>();
        for(String s: set) {
            if(!s.equals("_id")) {
                output.add(s);
            }
        }
        HashMap<String, Set<String>> map = new HashMap<>();
        map.put(moduleName, output);
        return map;
    }

    @RequestMapping("/getAllObjects")
    public Set<String> getCollections() {
        DB database = mongoClient.getDB("progresstracking-objects");
        Set<String> colls = database.getCollectionNames();
        return colls;
    }
}
