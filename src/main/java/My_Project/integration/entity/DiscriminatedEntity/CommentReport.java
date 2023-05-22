package My_Project.integration.entity.DiscriminatedEntity;

import My_Project.integration.entity.Report;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("COMMENT")
public class CommentReport extends Report {
}