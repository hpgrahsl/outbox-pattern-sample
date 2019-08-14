package com.github.hpgrahsl.ms.outbox.sample;

import com.github.hpgrahsl.ms.outbox.sample.model.PurchaseOrder;
import com.github.hpgrahsl.ms.outbox.sample.model.OrderLine;
import com.github.hpgrahsl.ms.outbox.sample.model.OrderLineStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class OutboxPatternSampleApplication {

    @Component
    public class SampleDataInitializer implements CommandLineRunner {

        @Autowired
        OrderService os;

        private final Logger logger = LoggerFactory.getLogger(SampleDataInitializer.class);

        @Override
        public void run(String... args) throws Exception {

            logger.info("creating sample data for orders...");
            PurchaseOrder po1 = new PurchaseOrder(1234, LocalDateTime.now(),
                    Arrays.asList(new OrderLine("ABC", 12, new BigDecimal(49.25)),
                            new OrderLine("XYZ", 98, new BigDecimal(99.25))));
            logger.info("placing order -> " + po1);
            os.placeOrder(po1);

            PurchaseOrder po2 = new PurchaseOrder(9876, LocalDateTime.now(),
                    Arrays.asList(new OrderLine("QWE", 5, new BigDecimal(10.25)),
                            new OrderLine("POI", 3, new BigDecimal(25.25)),
                            new OrderLine("STS", 7, new BigDecimal(5.25))));
            logger.info("placing order -> " + po2);
            os.placeOrder(po2);

            logger.info("updating some order lines");

            os.updateOrderLineStatus(po1.getId(), po1.getLineItems().get(0).getId(), OrderLineStatus.SHIPPED);
            os.updateOrderLineStatus(po1.getId(), po1.getLineItems().get(1).getId(), OrderLineStatus.CANCELLED);

            os.updateOrderLineStatus(po2.getId(), po2.getLineItems().get(0).getId(), OrderLineStatus.SHIPPED);
            os.updateOrderLineStatus(po2.getId(), po2.getLineItems().get(1).getId(), OrderLineStatus.CANCELLED);
            os.updateOrderLineStatus(po2.getId(), po2.getLineItems().get(2).getId(), OrderLineStatus.CANCELLED);

        }
    }

    public static void main(String[] args) {
        SpringApplication.run(OutboxPatternSampleApplication.class, args);
    }

}
