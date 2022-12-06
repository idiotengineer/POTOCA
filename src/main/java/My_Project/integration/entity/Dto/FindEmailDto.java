package My_Project.integration.entity.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FindEmailDto {
    private String phoneNumber;
    private String Name;
}
