package com.moz.ates.traffic;

import com.moz.ates.traffic.common.component.accident.TrafficAccidentIntegrationDto;
import com.moz.ates.traffic.common.component.enforcement.TrafficEnforcementComponent;
import com.moz.ates.traffic.common.component.enforcement.TrafficEnforcementIntegrationDto;
import com.moz.ates.traffic.policewebapp.PoliceWebAppApplication;
import com.moz.ates.traffic.policewebapp.tfcacdntmng.service.TrafficAcdntService;
import com.moz.ates.traffic.policewebapp.tfcenfmng.service.TrafficEnfceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Random;

@SpringBootTest(classes = PoliceWebAppApplication.class)
public class TrafficEnforcementGenerateTest {

    @Autowired
    TrafficEnfceService trafficEnfceService;

    @Autowired
    TrafficAcdntService trafficAcdntService;

    @Autowired
    TrafficEnforcementComponent trafficEnforcementComponent;

    @Test
    public void generateTempositoryData(){
        TrafficEnforcementIntegrationDto data = new TrafficEnforcementIntegrationDto();


        String[] firstNames = {"Anabela", "Cesário", "Dulce", "Eduardo", "Felismina", "Gildo", "Helena", "Ivo", "Joaquina", "Lúcio"};
        String[] lastNames = {"Mutemba", "Sigauque", "Machava", "Chirindza", "Nhantumbo", "Matavele", "Mucavele", "Baloi", "Mandlate", "Nkutumula"};

        for(int i = 0; i <30; i++) {
            // Create a random object
            Random random = new Random();

            // Generate random indexes
            int firstNameIndex = random.nextInt(firstNames.length);
            int lastNameIndex = random.nextInt(lastNames.length);

            // Create full name
            String fullName = firstNames[firstNameIndex] + " " + lastNames[lastNameIndex];
            ZoneId defaultZoneId = ZoneId.systemDefault();
            int licenseRand = generateRandomNumber(10000000, 10099999);
            int codeNo = generateRandomNumber(1, 9);
            data.setDvrLcenId(String.valueOf(licenseRand) + "/" + String.valueOf(codeNo));
            data.setVioNm(fullName);
            data.setVioBrth("13/03/2000");
            data.setVioPno("+258804705502");
            data.setVioEmail("email@email.com");
            data.setDvrLcenTy("1");
            data.setRoadAddr("Avenida Eduardo Mondlane, Maputo");

            int hh = generateRandomNumber(0, 23);
            int mm = generateRandomNumber(0, 59);
            String hhString = "";
            LocalDate localDate = LocalDate.of(2024, 4, 26);
            LocalTime localTime = LocalTime.of(hh, mm);
            data.setTfcEnfDt(LocalDateTime.of(localDate,localTime));
            int carNo = generateRandomNumber(100, 199);
            data.setVhRegNo("AHA" + String.valueOf(carNo) + "MC");
            data.setVhTy("C");

            int gisRand = generateRandomNumber(100000, 999999);
            String lng = "32." + gisRand;
            String lat = "-25." + gisRand;
            data.setLng(Float.valueOf(lng));
            data.setLat(Float.valueOf(lat));
            data.setPlacePymntId("0a8cb7277bcc4b70958c6e01052fd39d");
            data.setPymntOprtr("Diogo Pereira");
            data.setTotalPrice(2000L);
            data.setMozTfcEnfFineInfoJSONString("[{\"tfcLawFineId\":\"ddb08bde08ef41ff8a4c5cda6028dcac\",\"tfcEnfPrice\":\"2000\",\"tfcLawId\":\"3fe9567fe4ea4e7ca633996f5a70ce6a\"}]");
            trafficEnfceService.regTrafficEnfceInfo(data, "1", null);
        }
    }

    @Test
    public void generateTempositoryAccidentData(){
        TrafficAccidentIntegrationDto data = new TrafficAccidentIntegrationDto();

        for(int i = 0; i <30; i++) {

            data.setRoadAddr("Avenida Eduardo Mondlane, Maputo");

            int hh = generateRandomNumber(0, 23);
            int mm = generateRandomNumber(0, 59);
            LocalDate localDate = LocalDate.of(2024, 4, 26);
            LocalTime localTime = LocalTime.of(hh, mm);
            data.setAcdntDt(LocalDateTime.of(localDate,localTime));
            data.setAcdntTyCd("ACT005");
            data.setMozTfcAcdntTrgtInfoJsonStr("[{\"acdntTrgtCd\":\"ATT000\",\"dvrLcenId\":\"q68kyfM1\",\"vhRegNo\":\"VSwGeXOO\",\"vhTy\":\"PlTzTBRh\",\"dvYn\":\"Y\",\"acdntDmgCd\":\"ADC000\",\"acdntTrgtPno\":\"46Uk7Hk3\",\"acdntTrgtBrth\":\"fA5b3nyN\",\"acdntTrgtNm\":\"HB01Ol2s\",\"acdntTrgtSortNo\":0,\"deadYn\":\"Y\"}]");
            data.setAcdntChildYn("N");
            int gisRand = generateRandomNumber(100000, 999999);
            String lng = "33.5" + gisRand;
            String lat = "-25.9" + gisRand;
            data.setLng(Float.valueOf(lng));
            data.setLat(Float.valueOf(lat));
            trafficAcdntService.regTrafficAcdntInfo(data, "1", null);
        }
    }

    public static int generateRandomNumber(int min, int max) {
        return min + (int)(Math.random() * (max - min + 1));
    }

}
