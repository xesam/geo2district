package io.github.xesam.geo2district;

/**
 * 区域
 *
 * @author xesamguo@gmail.com
 */
public class District {

    private String adcode;
    private String name;
    private Boundary boundary;

    public District(String adcode) {
        this.adcode = adcode;
    }

    public String getAdcode() {
        return adcode;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public Boundary getBoundary() {
        return boundary;
    }

    public void setBoundary(Boundary boundary) {
        this.boundary = boundary;
    }

    @Override
    public String toString() {
        return "District{" +
                "adcode='" + adcode + '\'' +
                ", name='" + name + '\'' +
                ", boundary=" + boundary +
                '}';
    }
}
