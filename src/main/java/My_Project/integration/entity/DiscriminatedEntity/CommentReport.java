package My_Project.integration.entity.DiscriminatedEntity;

import My_Project.integration.entity.PostComments;
import My_Project.integration.entity.Report;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("COMMENT")
@Getter
@Setter
public class CommentReport extends Report {

    @ManyToOne(fetch = FetchType.LAZY)
    private PostComments postComments;
}
