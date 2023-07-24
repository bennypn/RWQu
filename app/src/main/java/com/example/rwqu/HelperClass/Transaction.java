package com.example.rwqu.HelperClass;

import java.util.HashMap;
import java.util.Map;

public class Transaction {
    private Map<String, Integer> votes;

    public Transaction() {
        votes = new HashMap<>();
        votes.put("Vote 1", 123);
        votes.put("Vote 2", 32);
        votes.put("Vote 3", 43);
    }

    public Map<String, Integer> getVotes() {
        return votes;
    }
}

