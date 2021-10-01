package com.redis.lars;

import java.util.Map;

import io.lettuce.core.dynamic.Commands;
import io.lettuce.core.dynamic.annotation.Command;

public interface RediSearchCommands extends Commands {

    @Command("FT.CREATE :indexName ON HASH PREFIX 1 :prefix SCHEMA :field TEXT PHONETIC dm:en")
    void create(String indexName, String prefix, String field);

    @Command("FT.DROPINDEX :indexName")
    void drop(String indexName);

    @Command("FT.SEARCH :indexName :term")
    Map<String, String> search(String indexName, String term);

    @Command("FT.SEARCH :indexName :term HIGHLIGHT")
    Map<String, String> searchWithHighlight(String indexName, String term);

}
