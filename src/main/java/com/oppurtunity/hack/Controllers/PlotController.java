package com.oppurtunity.hack.Controllers;

import com.mongodb.*;
import com.oppurtunity.hack.entities.DataWrapper;
import com.oppurtunity.hack.entities.DonationObject;
import com.oppurtunity.hack.entities.DonorObject;
import com.oppurtunity.hack.entities.ObjectDataModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/plot")
public class PlotController {

    @Autowired
    MongoClient mongoClient;

    @RequestMapping(value="/getDonors", method = RequestMethod.GET)
    public @ResponseBody
    List<DonorObject>  createCollection10() {
        DB database1 = mongoClient.getDB("progresstracking-events");
        DB database2 = mongoClient.getDB("progresstracking-objects");
        DBCollection eventCollection = database1.getCollection("Donation");
        DBCollection objectCollection = database2.getCollection("Donor");

        HashMap<String, Integer> count = new HashMap<>();
        List<DonorObject> donorObjects = new ArrayList<>();

        DBCursor curs = objectCollection.find();

        while(curs.hasNext()) {

            DBObject o = curs.next();
            String name = (String) o.get("Name") ;
            String email = (String) o.get("Email") ;
            String age = (String) o.get("Age") ;
            String city = (String) o.get("City") ;

            DonorObject object = new DonorObject(name, email, age, city);

            System.out.println(object.toString());

            donorObjects.add(object);
//            if(count.containsKey(key) && !key.equals("test")) {
//                count.put(key, count.get(key)+1);
//            } else {
//                count.put(key, 1);
//            }
        }

        return donorObjects;
    }

    @RequestMapping(value="/getDonations")
    public List<DonationObject> dcreateCollection2() {
        DB database1 = mongoClient.getDB("progresstracking-events");
        DB database2 = mongoClient.getDB("progresstracking-objects");
        DBCollection eventCollection = database1.getCollection("Donation");
        DBCollection objectCollection = database2.getCollection("Donor");


        List<DonationObject> donorObjects = new ArrayList<>();

        DBCursor curs = eventCollection.find();

        while(curs.hasNext()) {

            DBObject o = curs.next();
            String from = (String) o.get("From") ;
            String to = (String) o.get("To") ;
            String month = (String) o.get("Month") ;
            String year = (String) o.get("Year") ;
            String amount = (String) o.get("Amount");

            DonationObject object = new DonationObject(from, to, month, year, amount);

            System.out.println(object.toString());

            donorObjects.add(object);
        }

        return donorObjects;
    }


}
