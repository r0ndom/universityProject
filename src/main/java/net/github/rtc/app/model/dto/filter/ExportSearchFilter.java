package net.github.rtc.app.model.dto.filter;

import net.github.rtc.app.model.entity.export.ExportClasses;
import net.github.rtc.app.model.entity.export.ExportDetails;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ExportSearchFilter extends AbstractSearchFilter {

    private static final String PERCENT = "%";

    private String name;

    private ExportClasses exportClass;

    @Override
    public DetachedCriteria getCriteria() {
        final DetachedCriteria criteria = DetachedCriteria.forClass(ExportDetails.class);
        if (name != null && !("").equals(name)) {
            criteria.add(Restrictions.like("name", PERCENT + name + PERCENT));
        }
        if (exportClass != null) {
            criteria.add(Restrictions.eq("exportClass", exportClass));
        }
        return criteria;
    }

    @Override
    public Order order() {
        return Order.desc("createdDate");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExportClasses getExportClass() {
        return exportClass;
    }

    public void setExportClass(ExportClasses exportClass) {
        this.exportClass = exportClass;
    }
}
