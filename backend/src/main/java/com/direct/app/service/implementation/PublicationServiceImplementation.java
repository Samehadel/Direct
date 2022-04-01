package com.direct.app.service.implementation;

import com.direct.app.io.entities.PublicationEntity;
import com.direct.app.repositery.ConnectionRepository;
import com.direct.app.repositery.PublicationsRepository;
import com.direct.app.service.PublicationsService;
import com.direct.app.service.UserService;
import com.direct.app.service.util.publication_service_utils.PublishPostsUtil;
import com.direct.app.shared.dto.PublicationDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PublicationServiceImplementation implements PublicationsService {

    @Autowired
    private PublicationsRepository publicationRepo;

    @Autowired
    private ConnectionRepository connectionRepo;

    @Autowired
    private PublishPostsUtil publishPostsUtil;

    @Autowired
    private UserService userService;

    @Override
    public List<PublicationDto> retrievePublications() throws Exception {

        List<PublicationDto> publications = new ArrayList<>();

        // Get user's id from security context holder
        long userId = userService.retrieveUserId();

        //Repository use
        List<PublicationEntity> publicationEntities = publicationRepo.findByReceiverId(userId);

        for (PublicationEntity entity : publicationEntities) {
            PublicationDto model = new PublicationDto();

            //Copy relevant attributes
            model.setSenderId(entity.getSender().getId());

            BeanUtils.copyProperties(entity, model);
            BeanUtils.copyProperties(entity.getSender(), model.getSenderDetails());
            BeanUtils.copyProperties(entity.getSender().getUserDetails().getUserImage(), model.getSenderDetails());

            //Append publications
            publications.add(model);
        }

        return publications;
    }

    @Override
    public void publish(PublicationDto publication) throws Exception {
        com.direct.app.shared.dto.PublicationDto publicationDto = new com.direct.app.shared.dto.PublicationDto();

        // Retrieve User Id
        long senderId = userService.retrieveUserId();

        // Copy Properties from model to DTO
        BeanUtils.copyProperties(publication, publicationDto);
        publicationDto.setSenderId(senderId);

        // Delegate the whole function to the utility class "PublishPostsUtil"
        publishPostsUtil.publish(publicationDto);
    }

    @Transactional
    @Override
    public boolean markPublicationAsRead(long publicationId, boolean isRead) {
        int modifiedEntities = 0;

        if (isRead)
            modifiedEntities = publicationRepo.markPublicationAsRead(publicationId);
        else
            modifiedEntities = publicationRepo.markPublicationAsUnRead(publicationId);

            return modifiedEntities == 1 ? true : false;
    }

}
