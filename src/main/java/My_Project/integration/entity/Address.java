package My_Project.integration.entity;

import lombok.AllArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
public class Address {

    @Column(name = "city_name", length = 20, nullable = false)
    private String cityName;

    @Column(name = "town_name", length = 20)
    private String townName;

    @Column(name = "street_name", length = 20, nullable = false)
    private String streetName;

    @Column(name = "zip_code", length = 20)
    private String zipCode;

    @Column(name = "details",length = 20)
    private String detailsCode;

    protected Address() {
    }

}
