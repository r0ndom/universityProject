package net.github.rtc.app.dao;

import net.github.rtc.app.model.entity.AbstractPersistenceObject;

public interface EqualityChecker {
    void check(AbstractPersistenceObject sample, AbstractPersistenceObject another);
}
