package My_Project.integration.entity.DiscriminatedEntity;

import My_Project.integration.entity.BigComments;
import My_Project.integration.entity.Report;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("BIGCOMMENT")
@Getter
@Setter
public class BigCommentReport extends Report {

    @ManyToOne
    private BigComments bigComments;
}
