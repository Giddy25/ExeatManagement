package Spring.LoginRegister.Announcement;

import Spring.LoginRegister.Entity.Announcement;
import org.springframework.beans.factory.annotation.Autowired;

public interface AnnounceService {

    Announcement saveAnnouncement(Announcement announcement);
}
