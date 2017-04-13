package skillself.spring.sqlite.object;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Polytech
 * Created by igor on 04.04.17.
 */

@Data
@Builder
@ToString
public class ContactPhoneDetail {
    private Long id;
    private Long contactId;
    private String phoneType;
    private String phoneNumber;
}
