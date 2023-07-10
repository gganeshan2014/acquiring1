package com.cg.sp.util;

import com.cg.sp.entity.AcquiringPremiumEntity;
import com.cg.sp.entity.AcquiringStandardEntity;
import com.cg.sp.repository.AcquiringPremiumRepository;
import com.cg.sp.repository.AcquiringStandardRepository;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    AcquiringStandardRepository acquiringStandardRepository;
    @Autowired
    AcquiringPremiumRepository acquiringPremiumRepository;
    Faker faker;
    private static final Logger LOG = LoggerFactory.getLogger(DataInitializer.class);

    @Override
    public void run(String... args) throws ParseException {
        LocalDateTime startTime = LocalDateTime.now();
        if (acquiringStandardRepository.count() != 0 && acquiringPremiumRepository.count() != 0) {
            LOG.info("Database already exists. Skipping initialization for Database.");
            LOG.info("{} rows are already there for standard.", acquiringStandardRepository.count());
            LOG.info("{} rows are already there for premium.", acquiringPremiumRepository.count());
            LOG.info("{} milliseconds", Duration.between(startTime, LocalDateTime.now()).toMillis());
        } else {
            LOG.info("Initializing Database....");

            List<String> terminalIds = Arrays.asList("Terminal01", "Terminal02", "Terminal03", "Terminal04",
                    "Terminal05", "Terminal06", "Terminal07", "Terminal08", "Terminal09", "Terminal10");
            List<Double> amounts = Arrays.asList(1244.50, 14875.00, 36547.25, 12487.50, 125487.00, 121665.25, 14852.75, 1457.75, 1254.25, 15882.25, 15454.00);
            List<String> regions = Arrays.asList("Eastern", "Central");
            List<String> cities = Arrays.asList("Dammam", "Riyadh");
            HashMap<String, String> mccMap = new HashMap<>();
            mccMap.put("7895", "Groceries");
            mccMap.put("2424", "Eating Places, Restaurants");
            mccMap.put("6187", "Fast Food Restaurants");
            mccMap.put("3219", "Laundry, Cleaning, & Garment");
            mccMap.put("8989", "Service Stations");
            mccMap.put("1176", "Book Stores");
            List<String> mccValues = new ArrayList<>(mccMap.keySet());
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.DATE, 30);
            calendar.set(Calendar.MONTH, 3); //1 means Feb
            calendar.set(Calendar.YEAR, 2023);
            Date endDate = sdf.parse(sdf.format(calendar.getTime()));
            calendar.add(Calendar.MONTH, -1);
            Date startDate = sdf.parse(sdf.format(calendar.getTime()));

            for (int i = 0; i < 100; i++) {
                faker = new Faker();
                AcquiringPremiumEntity acquiringPremiumEntity = new AcquiringPremiumEntity();
                AcquiringStandardEntity acquiringStandardEntity = new AcquiringStandardEntity();

                acquiringPremiumEntity.setTerminalId(terminalIds.get(faker.number().numberBetween(0, 5)));
                acquiringStandardEntity.setTerminalId(acquiringPremiumEntity.getTerminalId());

                acquiringPremiumEntity.setTransactionDate(faker.date().between(sdf.parse(sdf.format(startDate)), sdf.parse(sdf.format(endDate))));
                acquiringStandardEntity.setTransactionDate(acquiringPremiumEntity.getTransactionDate());

                acquiringPremiumEntity.setChannel("PoS");
                acquiringStandardEntity.setChannel("PoS");

                acquiringPremiumEntity.setVolume(faker.number().numberBetween(10, 100));
                acquiringStandardEntity.setVolume(acquiringPremiumEntity.getVolume());

                acquiringPremiumEntity.setValue(amounts.get(faker.number().numberBetween(0, 9)));
                acquiringStandardEntity.setValue(acquiringPremiumEntity.getValue());

                acquiringPremiumEntity.setLocation(null);
                acquiringPremiumEntity.setCity(cities.get(faker.number().numberBetween(0, 2)));
                acquiringPremiumEntity.setRegion(regions.get(faker.number().numberBetween(0, 2)));
                acquiringPremiumEntity.setMcc(mccValues.get(faker.number().numberBetween(0, 6)));
                acquiringPremiumEntity.setMccDescription(mccMap.get(acquiringPremiumEntity.getMcc()));

                acquiringPremiumRepository.save(acquiringPremiumEntity);
                acquiringStandardRepository.save(acquiringStandardEntity);

            }
            LOG.info("{} rows inserted in premium.", acquiringPremiumRepository.count());
            LOG.info("{} rows inserted in standard.", acquiringStandardRepository.count());
            LOG.info("Database Initialized.");
            LOG.info("{} milliseconds", Duration.between(startTime, LocalDateTime.now()).toMillis());
        }
    }
}
