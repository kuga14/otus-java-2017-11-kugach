package ru.kugach.artem.otus.base;

import java.sql.SQLException;

public interface DBService extends AutoCloseable {

    String getMetaData();

}
