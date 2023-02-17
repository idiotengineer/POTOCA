package My_Project.integration.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
public class Address {

    @Column(name = "city_name", nullable = false) //도시명
    private String cityName;

    @Column(name = "town_name", length = 20) //동명
    private String townName;

    @Column(name = "street_name", length = 20, nullable = false) //도로명
    private String streetName;

    @Column(name = "zip_code", length = 20) // 우편번호
    private String zipCode;

    @Column(name = "details",length = 20) //상세주소
    private String detailsCode;

    public Address() {
    }

}
