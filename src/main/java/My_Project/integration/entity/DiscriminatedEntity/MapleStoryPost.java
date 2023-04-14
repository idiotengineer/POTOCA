package My_Project.integration.entity.DiscriminatedEntity;

import My_Project.integration.entity.PostInfo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MAPLESTORY")
public class MapleStoryPost extends PostInfo {
}
