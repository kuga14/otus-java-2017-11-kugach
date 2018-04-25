package ru.kugach.artem.otus.cache;

public interface CacheEngine<K ,V> {

    void put(K key, V value);

    V get(K key);

    void dispose();

    int getHitCount();

    int getMissCount();

}
