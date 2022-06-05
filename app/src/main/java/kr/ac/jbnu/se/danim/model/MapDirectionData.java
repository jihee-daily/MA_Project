package kr.ac.jbnu.se.danim.model;

public class MapDirectionData {
    private double startLat;
    private double startLng;
    private double endLat;
    private double endLng;

    public MapDirectionData() {
        this.startLat = 0.0;
        this.startLng = 0.0;
        this.endLat = 0.0;
        this.endLng = 0.0;
    }

    public MapDirectionData(double startLat, double startLng, double endLat, double endLng) {
        this.startLat = startLat;
        this.startLng = startLng;
        this.endLat = endLat;
        this.endLng = endLng;
    }

    public double getStartLat() {return startLat;}
    public void setStartLat(double startLat) {this.startLat = startLat;}

    public double getStartLng() {return startLng;}
    public void setStartLng(double startLng) {this.startLng = startLng;}

    public double getEndLat() {return endLat;}
    public void setEndLat(double endLat) {this.endLat = endLat;}

    public double getEndLng() {return endLng;}
    public void setEndLng(double endLng) {this.endLng = endLng;}
}
