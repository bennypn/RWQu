package com.example.rwqu.HelperClass;

public class Candidate {
    private String img;
    private String candidateName;
    private int numOfVotes;

    public Candidate() {
        // Default constructor required for Firebase
    }

    public Candidate(String img, String candidateName, int numOfVotes) {
        this.img = img;
        this.candidateName = candidateName;
        this.numOfVotes = numOfVotes;
    }

    public String getImg() {
        return img;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public int getNumOfVotes() {
        return numOfVotes;
    }
}
