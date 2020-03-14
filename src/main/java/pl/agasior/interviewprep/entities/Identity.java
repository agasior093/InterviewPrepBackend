package pl.agasior.interviewprep.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public abstract class Identity {
    @Id
    protected String id;
}
