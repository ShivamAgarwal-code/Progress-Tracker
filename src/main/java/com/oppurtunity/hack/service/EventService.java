package com.oppurtunity.hack.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@Service
public class EventService {

    @Autowired
    private MongoClient mongoClient;

    public Set<String> findEventAttributes(String eventName){
        DB db = mongoClient.getDB("progresstracking-events");
        DBCursor cur = db.getCollection(eventName).find();
        DBObject dbo = cur.next();
        Set<String> eventAttributes=dbo.keySet();
        return eventAttributes;
    }

    public void uploadFile(MultipartFile multipartFile,String eventName, String objectName) throws IOException {
        Set<String> eventAttributes = findEventAttributes(eventName);
        int inputNumofColumns=eventAttributes.size();
        File file = convertMultiPartToFile(multipartFile);
        DB database1 = mongoClient.getDB("progresstracking-events");
        DBCollection collection1 = database1.getCollection(eventName);
        DB database2 = mongoClient.getDB("progresstracking-objects");
        DBCollection collection2= database2.getCollection(objectName);
        String line = "";
        String[] columns =null;
        String cvsSplitBy = ",";
        int inputNumofColumnsinCSV=0;
        BasicDBObject doc = new BasicDBObject();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String header = br.readLine();
            if (header != null) {
                columns = header.split(",");
                inputNumofColumnsinCSV=columns.length;
            }
            if(inputNumofColumns==inputNumofColumnsinCSV) {
                while ((line = br.readLine()) != null) {
                    String[] eventData = line.split(cvsSplitBy);
                    ArrayList list = new ArrayList();
                    for (int i = 0; i < eventData.length; i++) {
                        doc.put(columns[i],eventData[i]);
                    }
                    collection1.insert(doc);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
