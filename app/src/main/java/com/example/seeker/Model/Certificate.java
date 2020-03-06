package com.example.seeker.Model;

public class Certificate {

    private String certificates;

    public Certificate(String certificates) {
        this.certificates = certificates;
    }

    public String getCertificates() {
        return certificates;
    }

    public void setCertificates(String certificates) {
        this.certificates = certificates;
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "certificates='" + certificates + '\'' +
                '}';
    }
}
