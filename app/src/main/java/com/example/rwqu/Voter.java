package com.example.rwqu;

public class Voter {
    private String voterNIK;
    private String voterName;
    private String voterUID;
    private int candidateNum; // Jika ada atribut ini pada beberapa data "Voter"

    public Voter() {
        // Diperlukan untuk Firebase
    }

    public String getVoterNIK() {
        return voterNIK;
    }

    public void setVoterNIK(String voterNIK) {
        this.voterNIK = voterNIK;
    }

    public String getVoterName() {
        return voterName;
    }

    public void setVoterName(String voterName) {
        this.voterName = voterName;
    }

    public String getVoterUID() {
        return voterUID;
    }

    public void setVoterUID(String voterUID) {
        this.voterUID = voterUID;
    }

    public int getCandidateNum() {
        return candidateNum;
    }

    public void setCandidateNum(int candidateNum) {
        this.candidateNum = candidateNum;
    }
}
