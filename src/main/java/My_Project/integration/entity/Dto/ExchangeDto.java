package My_Project.integration.entity.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ExchangeDto {

    private String email;
    private String password;
    private Optional<Long> point;
}
