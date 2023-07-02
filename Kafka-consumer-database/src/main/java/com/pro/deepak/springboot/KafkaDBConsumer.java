package com.pro.deepak.springboot;

import com.pro.deepak.springboot.entity.WikimediaData;
import com.pro.deepak.springboot.repository.WikimediaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDBConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDBConsumer.class);

    private WikimediaDataRepository dataRepository;

    public KafkaDBConsumer(WikimediaDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @KafkaListener(topics = "wikimedia_recentchange", groupId = "myGroup")

    public void consume(String eventMessage){
        LOGGER.info("Msg rcvd -> "+eventMessage);
        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setWikiEventData(eventMessage);

        dataRepository.save(wikimediaData);
    }
}
