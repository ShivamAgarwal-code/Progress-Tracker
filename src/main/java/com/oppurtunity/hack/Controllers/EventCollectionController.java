package com.oppurtunity.hack.Controllers;

import com.mongodb.*;
import com.oppurtunity.hack.entities.EventWrapper;
import com.oppurtunity.hack.entities.Module;
import com.oppurtunity.hack.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
public class EventCollectionController {

	@Autowired
	private MongoClient mongoClient;

	@Autowired
	private EventService eventService;

	@RequestMapping(value="/createEvent", consumes = "application/json")
	public ResponseEntity<Object> createCollection(@RequestBody EventWrapper objects) {
		try {
			System.out.println("event creation");
			DB database = mongoClient.getDB("progresstracking-events");
			DBCollection collection = database.createCollection(objects.getModuleName(), null);
			BasicDBObject document = new BasicDBObject();
			document.put("object", objects.getObjectName());
			for(Module mod : objects.getAttributes()) {
				document.put(mod.getLabel(), "test");
			}
			collection.insert(document);

			return ResponseEntity.status(HttpStatus.OK).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping("/get_all_events")
	public List<DBObject> getCollection() {
		DB database = mongoClient.getDB("progresstracking-events");
		DBCollection collection = database.getCollection("collections");
		DBCursor cursor = collection.find();
		List<DBObject> objects = new ArrayList<>();
		while(cursor.hasNext()) {
			objects.add(cursor.next());
		}
		return objects;
	}



    @RequestMapping("/delete_events")
    public void deleteCollections() {
        DB database = mongoClient.getDB("progresstracking-events");
        database.dropDatabase();
    }

}
