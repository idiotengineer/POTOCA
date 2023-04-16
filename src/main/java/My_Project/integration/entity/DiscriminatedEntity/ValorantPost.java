package My_Project.integration.entity.DiscriminatedEntity;

import My_Project.integration.entity.Dates;
import My_Project.integration.entity.Dto.PostInfoDto;
import My_Project.integration.entity.PostInfo;
import My_Project.integration.entity.PostLikeAndDislike;
import My_Project.integration.entity.Users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.HashSet;

@Entity
@DiscriminatorValue("VALORANT")
public class ValorantPost extends PostInfo {

}
