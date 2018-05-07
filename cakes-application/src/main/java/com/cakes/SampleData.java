package com.cakes;

import com.cakes.domain.Cake;
import com.cakes.repository.CakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Insert sample data to the MongoDB
 */
@Component
public class SampleData implements CommandLineRunner {

    @Autowired
    private CakeRepository cakeRepository;

    @Override
    public void run(String... args) throws Exception {
        Cake cake = new Cake();
        cake.setDescription("description");
        cake.setId("89431039-246c-4465-84cc-f2af43ecfb82");
        cake.setImage("http://namebirthdaycakes.net/img/amazing-red-velvet-cake-for-birthday-wishes-with-name/88.jpg");
        cake.setPrice(100.0);
        cake.setQuantity(1);
        cakeRepository.save(cake);

        Cake cake2 = new Cake();
        cake2.setDescription("description2");
        cake2.setId("9d8cd868-1dfe-466a-be4b-cfc16cf69dd1");
        cake2.setImage("http://namebirthdaycakes.net/img/amazing-red-velvet-cake-for-birthday-wishes-with-name/88.jpg");
        cake2.setPrice(1.0);
        cake2.setQuantity(100);
        cakeRepository.save(cake2);
    }
}
