package My_Project.integration.entity.DiscriminatedEntity;

import My_Project.integration.entity.PostInfo;
import My_Project.integration.entity.Report;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("POST")
public class PostReport extends Report {

    @ManyToOne(fetch = FetchType.LAZY)
    private PostInfo postInfo;
}
