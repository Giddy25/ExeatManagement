package Spring.LoginRegister.Announcement;

import Spring.LoginRegister.Entity.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnnounceServiceImpl implements AnnounceService{
    @Autowired
    AnnouncementRepository announcementRepository;
    @Override
    public Announcement saveAnnouncement(Announcement announcement) {
        return announcementRepository.save(announcement);
    }
}
