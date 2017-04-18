package skillself.spring.sqlite.object;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Polytech
 * Created by igor on 04.04.17.
 */

@Data
@Builder
@ToString
@EqualsAndHashCode
//@ToString(exclude = {"contactPhoneDetailList"})
public class Contact {
    private final Long id;

    private final String firstName;
    private final String lastName;
    private final Date birthDate;

    private final List<ContactPhoneDetail> contactPhoneDetailList = new ArrayList<>();

    public ContactPhoneDetail addPhoneDetail(final ContactPhoneDetail contactPhoneDetail) {
        contactPhoneDetailList.add(contactPhoneDetail);
        return contactPhoneDetail;
    }
}
