package net.github.rtc.app.service.export;

import net.github.rtc.app.model.entity.export.ExportDetails;
import net.github.rtc.app.service.generic.GenericService;

import java.io.File;

public interface ExportService extends GenericService<ExportDetails> {
    /**
     * Compile export from export details
     * @param export details of the export
     * @return updated export
     */
    ExportDetails compileExport(ExportDetails export);

    /**
     * Create File object from export details
     * @param details details of the export
     * @return new File object with export details
     */
    File getExport(ExportDetails details);

    /**
     * Get correctly encoded name of file
     * @param export details of the export
     * @param agent information about browser
     * @return correct name
     */
    String getCorrectlyEncodedNameFile(ExportDetails export, String agent);
}
