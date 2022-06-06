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
    public double setStartLat(double startLat) {this.startLat = startLat;
        return startLat;
    }

    public double getStartLng() {return startLng;}
    public double setStartLng(double startLng) {this.startLng = startLng;
        return startLng;
    }

    public double getEndLat() {return endLat;}
    public double setEndLat(double endLat) {this.endLat = endLat;
        return endLat;
    }

    public double getEndLng() {return endLng;}
    public double setEndLng(double endLng) {this.endLng = endLng;
        return endLng;
    }
}
