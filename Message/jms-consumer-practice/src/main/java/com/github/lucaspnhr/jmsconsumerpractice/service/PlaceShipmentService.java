package com.github.lucaspnhr.jmsconsumerpractice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class PlaceShipmentService {

    private Logger log = LoggerFactory.getLogger(PlaceShipmentService.class);


    @JmsListener(destination = "${jms.queue}")
    public void placeShipment(String shipmentDetail){
        int divisorIndex = (shipmentDetail.indexOf('-'));
        int weight = Integer.parseInt(shipmentDetail.substring(0,divisorIndex-1));
        String productName = shipmentDetail.substring(divisorIndex + 1);

        if(weight > 50){
            log.info("shiping {} with total price R${}", productName,weight*10);
        }else if(weight < 50){
            log.info("shiping {} with total price R${}", productName,weight*5);
        }
    }
}
