package pl.agasior.interviewprep.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public abstract class Identity {
    @Id
    protected String id;
}
