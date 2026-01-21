package se.uhr.simone.restbucks.control;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.quarkus.scheduler.Scheduled;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.uhr.simone.core.SimOne;

import static io.quarkus.scheduler.Scheduled.ConcurrentExecution.*;

@ApplicationScoped
public class RestBucksScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(RestBucksScheduler.class);

    @Inject
    SimOne simOne;

    @Scheduled(every = "{worker.schedule.expr}", concurrentExecution = SKIP)
    @Transactional
    void scheduled() {
        try {
            simOne.connectEntrysToFeeds();
            simOne.createXmlForFeeds();
        } catch (Exception e) {
            LOG.error("timer event error", e);
        }
    }
}
