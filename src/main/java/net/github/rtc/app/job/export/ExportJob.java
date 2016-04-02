package net.github.rtc.app.job.export;



import net.github.rtc.app.model.entity.export.ExportDetails;
import net.github.rtc.app.service.export.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Job that updates export files on server every 15 minutes
 */
@Component
public class ExportJob {
    @Autowired
    private ExportService exportService;

    /**
     * Updating export files every 15 minutes according to changes in db
     */
    @Scheduled(cron = "0 0/15 * * * ?")
    public void reportUpdate() {
        final List<ExportDetails> myList = exportService.findAll();
        for (ExportDetails export : myList) {
            exportService.compileExport(export);
        }
    }
}
